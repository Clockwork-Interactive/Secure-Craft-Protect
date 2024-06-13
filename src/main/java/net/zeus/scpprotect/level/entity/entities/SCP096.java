package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.refractionapi.refraction.sound.SoundUtil;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.configs.SCPServerConfig;
import net.zeus.scpprotect.level.entity.goals.BreakDoorGoal096;
import net.zeus.scpprotect.level.entity.goals.HurtByTargetGoal096;
import net.zeus.scpprotect.level.entity.goals.WaterAvoiding096StrollGoal;
import net.zeus.scpprotect.level.entity.goals.navigation.AnomalyNavigation;
import net.zeus.scpprotect.level.entity.goals.navigation.SCP096Navigation;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.util.SCPDamageTypes;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SCP096 extends SCPEntity implements NeutralMob {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Set<LivingEntity> targets = new HashSet<>();
    private float speedModifier = 0.33F;
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E292A0");
    private static final EntityDataAccessor<Boolean> DATA_IS_TRIGGERED = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CHARGE_TIME = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CAN_TRIGGER = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_HAD_TARGET = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);

    public static final RawAnimation CLIMBING_ANIMATION = RawAnimation.begin().then("climbing", Animation.LoopType.LOOP);
    public static final RawAnimation CROUCHING_ANIMATION = RawAnimation.begin().then("crouch", Animation.LoopType.LOOP);
    public static final RawAnimation SITTING_ANIMATION = RawAnimation.begin().then("sitting", Animation.LoopType.HOLD_ON_LAST_FRAME);
    public static final RawAnimation TRIGGERED_ANIMATION = RawAnimation.begin().then("triggered", Animation.LoopType.HOLD_ON_LAST_FRAME);
    public static final RawAnimation RUNNING_ANIMATION = RawAnimation.begin().then("running", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().then("idle", Animation.LoopType.LOOP);
    public static final RawAnimation WALKING_ANIMATION = RawAnimation.begin().then("walking", Animation.LoopType.LOOP);
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    public SCP096LookForPlayerGoal lookForPlayerGoal;

    private AnomalyNavigation regularNavigation;
    private SCP096Navigation scp096Navigation;

    public SCP096(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.lookForPlayerGoal = new SCP096LookForPlayerGoal(this, this::isAngryAt);
        this.targetSelector.addGoal(0, this.lookForPlayerGoal);
        this.targetSelector.addGoal(1, new HurtByTargetGoal096(this));

        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    @Override
    protected void onEffectAdded(MobEffectInstance pEffectInstance, @org.jetbrains.annotations.Nullable Entity pEntity) {
        this.removeAllEffects();
        super.onEffectAdded(pEffectInstance, pEntity);
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreakDoorGoal096(this, 20));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoiding096StrollGoal(this, 1.0D, 0.001F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 99.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 50000.0F)
                .add(Attributes.FOLLOW_RANGE, 1000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
                .add(ForgeMod.ENTITY_REACH.get(), 6.5F)
                .add(ForgeMod.SWIM_SPEED.get(), 8.0F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.0F);
    }

    public void onKill() {
        this.playSound(this.getKillSound(), 0.222F, 1.0F);
    }

    public void onKillAll() {
        this.setCanTrigger(true);
        this.setHasHadTarget(false);
        this.setChargeTime(this.getDefaultChargeTime());
    }

    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
        AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (pLivingEntity == null) {
            this.entityData.set(DATA_IS_TRIGGERED, false);
            this.entityData.set(DATA_STARED_AT, false);
            SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.entityData.set(DATA_IS_TRIGGERED, true);
            if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                this.speedModifier = Mth.lerp(Math.min(pLivingEntity.distanceTo(this) / 50.0F, 1.0F), 0.2F, 0.45F);
                SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

        super.setTarget(pLivingEntity);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity)
            pEntity.hurt(RefractionMisc.damageSource(SCPDamageTypes.SCP_096_DAMAGE, this), livingEntity.getMaxHealth() * 2);
        return true;
    }

    @Override
    public void tick() {
        if (!level().isClientSide) {
            LivingEntity target = this.getTarget();
            if (this.getChargeTime() == 0 || this.getChargeTime() == this.getDefaultChargeTime()) {
                this.setClimbing(this.horizontalCollision && !this.minorHorizontalCollision && this.level().getBlockState(this.blockPosition().above()).isAir() && target != null && target.getY() - this.getY() >= 2.0D);
            }
            if (this.targets.isEmpty() && this.hasHadTarget()) { // Do stuff when all targets are dead.
                this.onKillAll();
            }
        }
        super.tick();
    }

    public int getDefaultChargeTime() {
        return 580;
    }

    public boolean isDefaultCharge() {
        return this.getChargeTime() == this.getDefaultChargeTime();
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        this.addDefault("idle", IDLE_ANIMATION);
        this.addContinuous(0, "climbing", CLIMBING_ANIMATION, (state) -> this.isClimbing());
        this.addContinuous(1, "triggered", TRIGGERED_ANIMATION, (state) -> !this.isDefaultCharge() && !this.isTriggered());
        this.addContinuous(2, "running", RUNNING_ANIMATION, (state) -> this.isTriggered() && state.isMoving());
        this.addContinuous(3, "walking", WALKING_ANIMATION, (state) -> Vec3Helper.isEntityMovingClient(this));
        this.addContinuous(4, "sitting", SITTING_ANIMATION, (state) -> this.entityData.get(SITTING));

        controllerRegistrar.add(new AnimationController<>(this, "controller", (state) -> PlayState.STOP)
                .triggerableAnim("triggered", TRIGGERED_ANIMATION)
                .triggerableAnim("running", RUNNING_ANIMATION)
                .triggerableAnim("walking", WALKING_ANIMATION)
                .triggerableAnim("crouch", CROUCHING_ANIMATION)
                .triggerableAnim("sitting", SITTING_ANIMATION)
                .triggerableAnim("climbing", CLIMBING_ANIMATION)
                .triggerableAnim("idle", IDLE_ANIMATION));
    }

    @Override
    public boolean doArmAnimations(AnimationState<?> state) {
        return false;
    }

    @Override
    public boolean doLegAnimations(AnimationState<?> state) {
        return false;
    }

    @Override
    public boolean doHeadAnimation(AnimationState<?> state) {
        if (this.isCurrentAnimation(state, CROUCHING_ANIMATION) || !state.isMoving()) {
            return false;
        }
        return !this.isCurrentAnimation(state, SITTING_ANIMATION) && !this.isCurrentAnimation(state, TRIGGERED_ANIMATION) && !this.isCurrentAnimation(state, CROUCHING_ANIMATION);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        this.regularNavigation = new AnomalyNavigation(this, pLevel);
        this.scp096Navigation = new SCP096Navigation(this, pLevel);
        return this.regularNavigation;
    }

    @Override
    public PathNavigation getNavigation() {
        if (this.getTarget() != null && this.getTarget().getY() - this.getY() >= 2.0D) {
            return this.scp096Navigation;
        }
        return super.getNavigation();
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }

    public void setClimbing(boolean pClimbing) {
        byte b0 = this.entityData.get(CLIMBING);
        if (this.getTarget() == null) {
            if (!this.isClimbing()) return;
            b0 = (byte) (b0 & -2);
        }
        if (pClimbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.entityData.set(CLIMBING, b0);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pPose == Pose.STANDING ? 2.1F : pSize.height - 0.1F;
    }

    public boolean isLookingAtMe(LivingEntity livingEntity) {
        boolean can096SeeEntity = Vec3Helper.isInAngle(this, livingEntity.getEyePosition(), 180);
        boolean canEntitySee096 = Vec3Helper.isInAngle(livingEntity, this.getEyePosition(), 120);
        return can096SeeEntity && canEntitySee096 && livingEntity.hasLineOfSight(this) && this.inRange(livingEntity);
    }

    public boolean inRange(LivingEntity livingEntity) {
        return livingEntity.level() instanceof ServerLevel serverLevel && this.distanceTo(livingEntity) <= serverLevel.getServer().getPlayerList().getSimulationDistance() * 16;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_TRIGGERED, false);
        this.entityData.define(DATA_STARED_AT, false);
        this.entityData.define(DATA_CAN_TRIGGER, true);
        this.entityData.define(DATA_CHARGE_TIME, this.getDefaultChargeTime());
        this.entityData.define(DATA_HAS_HAD_TARGET, false);
        this.entityData.define(CLIMBING, (byte) 0);
        this.entityData.define(SITTING, false);
    }

    protected SoundEvent getTriggeredSound() {
        return SCPSounds.SCP_096_TRIGGERED.get();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.FALL) || pSource.is(DamageTypes.DROWN) || pSource.is(DamageTypes.IN_WALL)) {
            return false;
        } else {
            return super.hurt(pSource, pAmount);
        }
    }

    protected SoundEvent getKillSound() {
        return SCPSounds.SCP_096_KILL.get();
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    //Data Stuff

    @Override
    public boolean canContain() {
        return true;
    }

    public boolean isTriggered() {
        return this.entityData.get(DATA_IS_TRIGGERED);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_IS_TRIGGERED.equals(pKey) && this.hasBeenStaredAt() && this.level().isClientSide) {
            this.playAmbientSound();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public boolean hasBeenStaredAt() {
        return this.entityData.get(DATA_STARED_AT);
    }

    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, true);
    }

    public boolean hasHadTarget() {
        return this.entityData.get(DATA_HAS_HAD_TARGET);
    }

    public void setHasHadTarget(boolean hasHadTarget) {
        this.entityData.set(DATA_HAS_HAD_TARGET, hasHadTarget);
    }

    public int getChargeTime() {
        return this.entityData.get(DATA_CHARGE_TIME);
    }

    public void setChargeTime(int time) {
        this.entityData.set(DATA_CHARGE_TIME, time);
    }

    public boolean canTrigger() {
        return this.entityData.get(DATA_CAN_TRIGGER);
    }

    public void setCanTrigger(boolean canTrigger) {
        this.entityData.set(DATA_CAN_TRIGGER, canTrigger);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    public static class SCP096LookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final SCP096 scp096;
        @Nullable
        protected LivingEntity pendingTarget;
        private int aggroTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
        public final Predicate<LivingEntity> isAngerInducing;

        public SCP096LookForPlayerGoal(SCP096 scp096, @Nullable Predicate<LivingEntity> pSelectionPredicate) {
            super(scp096, Player.class, 0, false, false, pSelectionPredicate);
            this.scp096 = scp096;

            this.isAngerInducing = (entity) -> {
                if (this.scp096.isLookingAtMe(entity) && this.scp096.canTrigger() && !this.scp096.targets.contains(entity)) {

                    if (entity instanceof Player player && (player.isCreative() || player.isSpectator()))
                        return false;

                    if (entity instanceof ServerPlayer player) {
                        if (player.isCreative()) {
                            return false;
                        }

                        SoundUtil.playLocalSound(player, SCPSounds.SCP_096_SEEN.get());
                    }
                    if (entity instanceof Player player) {
                        SCPAdvancements.grant(player, SCPAdvancements.DONT_LOOK_AT_ME);
                    }
                    this.add(entity);
                }

                return scp096.isAngryAt(entity) || this.scp096.targets.contains(entity);
            };

            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing).ignoreLineOfSight();
        }

        public void add(LivingEntity entity) {
            if (!(entity instanceof Player) && !SCPServerConfig.SCP_096_REACT_TO_ENTITIES.get()) return;
            if ((entity instanceof Player player && (player.isCreative() || player.isSpectator())) || !this.scp096.canTrigger())
                return;
            if (this.scp096.targets.contains(entity)) return;
            this.scp096.targets.add(entity);
            if (this.pendingTarget == null) {
                this.pendingTarget = entity;
            }
        }

        public boolean canUse() {
            this.refreshTargetList();
            this.pendingTarget = this.scp096.level().getNearestEntity(this.scp096.level().getEntitiesOfClass(LivingEntity.class, this.scp096.getBoundingBox().inflate(this.scp096.getAttributeValue(Attributes.FOLLOW_RANGE))), this.startAggroTargetConditions, this.scp096, this.scp096.getX(), this.scp096.getY(), this.scp096.getZ());

            if (this.scp096.targets.isEmpty()) {
                return false;
            } else if (this.pendingTarget == null) {
                this.pendingTarget = this.scp096.targets.iterator().next();
            }

            if (this.scp096.getChargeTime() >= 0) {
                this.scp096.setChargeTime(this.scp096.getChargeTime() - 2); // Have to remove 2 instead of 1 because this only fires every 2 ticks.
                ServerLevel serverLevel = (ServerLevel) this.scp096.level();
                serverLevel.sendParticles(ParticleTypes.RAIN, this.scp096.position().x, this.scp096.getEyeY(), this.scp096.position().z, 1, -0.2F, -0.1F, 0.0F, 1);
                if (this.scp096.getChargeTime() == this.scp096.getDefaultChargeTime() - 2) {
                    this.scp096.playSound(this.scp096.getTriggeredSound(), 2.0F, 1.0F);
                    this.scp096.setClimbing(false);
                    RefractionMisc.enableMovement(this.scp096, this.scp096.getDefaultChargeTime());
                    if (scp096.moveControl.hasWanted())
                        this.scp096.moveTo(this.scp096.position());
                }
            }

            return !this.scp096.targets.isEmpty() && this.scp096.getChargeTime() <= 0;
        }

        public boolean canContinueToUse() {
            this.refreshTargetList();
            if (this.pendingTarget != null && this.scp096.getChargeTime() <= 0) {
                if (!this.isAngerInducing.test(this.pendingTarget)) {
                    return false;
                } else {
                    this.scp096.lookAt(EntityAnchorArgument.Anchor.EYES, this.pendingTarget.position());
                    return true;
                }
            } else {
                return this.target != null && this.continueAggroTargetConditions.test(this.scp096, this.target) || super.canContinueToUse();
            }
        }

        public void start() {
            this.scp096.setBeingStaredAt();
        }

        public void tick() {
            if (this.scp096.getTarget() == null) {
                super.setTarget(null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    this.scp096.setHasHadTarget(true);
                    super.start();
                }
            }
        }


        public void refreshTargetList() {
            Iterator<LivingEntity> iterator = this.scp096.targets.iterator();

            while (iterator.hasNext()) {
                LivingEntity entity = iterator.next();
                if (entity.isDeadOrDying() || entity.isRemoved() || (entity instanceof ServerPlayer serverPlayer && serverPlayer.isCreative())) {
                    this.scp096.onKill();
                    this.scp096.setHasHadTarget(true);
                    iterator.remove();
                }
            }

            this.scp096.targets = this.scp096.targets.stream().sorted(Comparator.comparingDouble(this.scp096::distanceTo)).collect(Collectors.toCollection(LinkedHashSet::new));
            this.pendingTarget = this.scp096.targets.isEmpty() ? null : this.scp096.targets.iterator().next();
        }

    }

}