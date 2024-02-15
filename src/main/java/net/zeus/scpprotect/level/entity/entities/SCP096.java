package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.configs.SCPCommonConfig;
import net.zeus.scpprotect.level.entity.entities.goals.BreakDoorGoal096;
import net.zeus.scpprotect.level.entity.entities.goals.HurtByTargetGoal096;
import net.zeus.scpprotect.level.entity.entities.goals.WaterAvoiding096StrollGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.ModSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.PlayLocalSeenSoundS2C;
import net.zeus.scpprotect.util.RunnableCooldownHandler;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.function.Predicate;

public class SCP096 extends Monster implements GeoEntity, Anomaly, NeutralMob {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final List<LivingEntity> targetMap = new ArrayList<>();
    public final int defaultChargeTime = 580;
    private float speedModifier = 0.33F;
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E292A0");
    private static final EntityDataAccessor<Boolean> DATA_IS_TRIGGERED = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CHARGE_TIME = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CAN_TRIGGER = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_HAD_TARGET = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BYTE);

    public SCP096(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(0, new SCP096.SCP096LookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(1, new HurtByTargetGoal096(this));

        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(2, new BreakDoorGoal096(this, 20));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoiding096StrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 99.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 50000.0F)
                .add(Attributes.FOLLOW_RANGE, 1000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
                .add(ForgeMod.ENTITY_REACH.get(), 5.0F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 3.0F);
    }


    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "c", (state) -> {
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
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

    boolean isLookingAtMe(LivingEntity livingEntity) {
        Vec3 vec3 = livingEntity.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - livingEntity.getX(), this.getEyeY() - livingEntity.getEyeY(), this.getZ() - livingEntity.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 && livingEntity.hasLineOfSight(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_TRIGGERED, false);
        this.entityData.define(DATA_STARED_AT, false);
        this.entityData.define(DATA_CAN_TRIGGER, true);
        this.entityData.define(DATA_CHARGE_TIME, defaultChargeTime);
        this.entityData.define(DATA_HAS_HAD_TARGET, false);
        this.entityData.define(CLIMBING, (byte) 0);
    }

    protected SoundEvent getTriggeredSound() {
        return ModSounds.SCP_096_TRIGGERED.get();
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
        return ModSounds.SCP_096_KILL.get();
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
                this.speedModifier = pLivingEntity.distanceTo(this) / 20;
                if (this.speedModifier < 0.2F) {
                    this.speedModifier = 0.2F;
                }
                if (this.speedModifier > 0.35F) {
                    this.speedModifier = 0.35F;
                }
                SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

        super.setTarget(pLivingEntity);
    }

    @Override
    public void tick() {
        if (!level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
            if (this.targetMap.isEmpty() && this.hasHadTarget()) { // Do stuff when all targets are dead.
                if (SCPCommonConfig.SCP096Cooldown.get()) {
                    this.setCanTrigger(false);
                    RunnableCooldownHandler.addDelayedRunnable(() -> {
                        if (!this.isDeadOrDying()) {
                            this.setCanTrigger(true);
                        }
                    }, 600);
                }
                this.setHasHadTarget(false);
                this.setChargeTime(defaultChargeTime);
            }
        }
        super.tick();
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    //Data Stuff

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
        if (!SCPCommonConfig.SCP096Cooldown.get()) {
            return true;
        }
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

    static class SCP096LookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final SCP096 scp096;
        @Nullable
        private LivingEntity pendingTarget;
        private int aggroTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
        private final Predicate<LivingEntity> isAngerInducing;

        public SCP096LookForPlayerGoal(SCP096 scp096, @Nullable Predicate<LivingEntity> pSelectionPredicate) {
            super(scp096, Player.class, 0, false, false, pSelectionPredicate);
            this.scp096 = scp096;

            this.isAngerInducing = (entity) -> {
                if (this.scp096.isLookingAtMe(entity) && this.scp096.canTrigger() && !this.scp096.targetMap.contains(entity)) {

                    if (entity instanceof ServerPlayer player) {
                        if (player.isCreative()) {
                            return false;
                        }

                        ModMessages.sendToPlayer(new PlayLocalSeenSoundS2C(), player);
                    }

                    this.scp096.targetMap.add(entity);
                }

                return scp096.isAngryAt(entity) || this.scp096.targetMap.contains(entity);
            };

            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing).ignoreLineOfSight();
        }

        public boolean canUse() {
            this.refreshTargetList();
            this.pendingTarget = this.scp096.level().getNearestEntity(this.scp096.level().getEntitiesOfClass(LivingEntity.class, this.scp096.getBoundingBox().inflate(this.scp096.getAttributeValue(Attributes.FOLLOW_RANGE))), this.startAggroTargetConditions, this.scp096, this.scp096.getX(), this.scp096.getY(), this.scp096.getZ());

            if (this.scp096.targetMap.isEmpty()) {
                return false;
            }

            if (this.scp096.getChargeTime() >= 0) {
                this.scp096.setChargeTime(this.scp096.getChargeTime() - 2); // Have to remove 2 instead of 1 because this only fires every 2 ticks.
                ServerLevel serverLevel = (ServerLevel) this.scp096.level();
                serverLevel.sendParticles(ParticleTypes.RAIN, this.scp096.position().x, this.scp096.getEyeY(), this.scp096.position().z, 1, -0.2F, -0.1F, 0.0F, 1);
                if (this.scp096.getChargeTime() == this.scp096.defaultChargeTime - 2) {
                    this.scp096.playSound(this.scp096.getTriggeredSound(), 2.0F, 1.0F);
                }
            }

            return !this.scp096.targetMap.isEmpty() && this.scp096.getChargeTime() <= 0;
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
            ListIterator<LivingEntity> iterator = this.scp096.targetMap.listIterator();

            while (iterator.hasNext()) {
                LivingEntity entity = iterator.next();
                if (entity.isDeadOrDying() || entity.isRemoved()) {
                    this.scp096.playSound(this.scp096.getKillSound(), 1.0F, 1.0F);
                    this.scp096.setHasHadTarget(true);
                    iterator.remove();
                }
            }

        }

    }

}