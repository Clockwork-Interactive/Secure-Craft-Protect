package net.zeus.scppancakes.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scppancakes.custom.RunnableCooldownHandler;
import net.zeus.scppancakes.entity.custom.goals.BreakDoorGoal096;
import net.zeus.scppancakes.entity.custom.goals.HurtByTargetGoal096;
import net.zeus.scppancakes.entity.custom.goals.WaterAvoiding096StrollGoal;
import net.zeus.scppancakes.sound.ModSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static net.zeus.scppancakes.event.CommonEvents.currentPose;

public class SCP096 extends Monster implements GeoEntity, NeutralMob {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E292A0");
    private float speedModifier = 0.33F;
    public static float HITBOX_WIDTH = 0.5F;
    public static float HITBOX_HEIGHT = 2.3F;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> DATA_ISTRIGGERED = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CHARGE_TIME = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CAN_TRIGGER = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_HAD_TARGET = SynchedEntityData.defineId(SCP096.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.7F, 1.3F);
    public final Set<LivingEntity> targetMap = new HashSet<>();
    private final int defaultChargeTime = 580;
    private int targetChangeTime;

    public SCP096(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(0, new SCP096LookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(1, new HurtByTargetGoal096(this));

        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level));
        this.goalSelector.addGoal(2, new BreakDoorGoal096(this, 20));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoiding096StrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 99.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 700.0F)
                .add(Attributes.FOLLOW_RANGE, 1000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
                .add(ForgeMod.ATTACK_RANGE.get(), 5.0F);
    }

    protected SoundEvent getTriggeredSound() {
        return ModSounds.SCP_096_TRIGGERED.get();
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.SCP_096_IDLE.get();
    }

    protected SoundEvent getKillSound() {
        return ModSounds.SCP_096_KILL.get();
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setDeltaMovement(this.getDeltaMovement().add(0, 0.3, 0));
    }

    @Override
    public void playAmbientSound() {
        if (this.getChargeTime() == defaultChargeTime) {
            SoundEvent soundevent = this.getAmbientSound();
            if (soundevent != null) {
                this.playSound(soundevent, 0.5F, this.getVoicePitch());
            }
        }
    }


    public static final RawAnimation WALK096 = RawAnimation.begin().thenLoop("scp_096_walking");
    public static final RawAnimation RUN096 = RawAnimation.begin().thenLoop("scp_096_run");
    public static final RawAnimation IDLE096 = RawAnimation.begin().thenLoop("scp_096_sitting_idle");
    public static final RawAnimation TRIGGERED096 = RawAnimation.begin().thenLoop("scp_096_triggered");
    public static final RawAnimation LOSTTARGET096 = RawAnimation.begin().thenLoop("scp_096_lost_target");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 0, state -> {
            if (this.getChargeTime() != defaultChargeTime && this.getChargeTime() != 0) {
                state.setAndContinue(TRIGGERED096);
                currentPose.put(this, Pose.STANDING);
            } else if (!this.canTrigger()) {
                state.setAndContinue(LOSTTARGET096);
                currentPose.put(this, Pose.STANDING);
            } else if (state.isMoving() && this.isTriggered()) {
                state.setAndContinue(RUN096);
                currentPose.put(this, Pose.STANDING);
            } else if (state.isMoving() && !this.isTriggered()) {
                state.setAndContinue(WALK096);
                currentPose.put(this, Pose.STANDING);
            } else if (!state.isMoving() && !this.isTriggered()) {
                state.setAndContinue(IDLE096);
                currentPose.put(this, Pose.SITTING);
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return pPose == Pose.SITTING ? SITTING_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pPose);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height - 0.1F;
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
        this.entityData.define(DATA_ISTRIGGERED, false);
        this.entityData.define(DATA_STARED_AT, false);
        this.entityData.define(DATA_CAN_TRIGGER, true);
        this.entityData.define(DATA_CHARGE_TIME, defaultChargeTime);
        this.entityData.define(DATA_HAS_HAD_TARGET, false);
    }

    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
        AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (pLivingEntity == null) {
            this.targetChangeTime = 0;
            this.entityData.set(DATA_ISTRIGGERED, false);
            this.entityData.set(DATA_STARED_AT, false);
            SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.targetChangeTime = this.tickCount;
            this.entityData.set(DATA_ISTRIGGERED, true);
            if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                this.speedModifier = pLivingEntity.distanceTo(this) / 20;
                if (this.speedModifier < 0.2F) {
                    this.speedModifier = 0.2F;
                }
                if (this.speedModifier > 0.4F) {
                    this.speedModifier = 0.4F;
                }
                SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", this.speedModifier, AttributeModifier.Operation.ADDITION);
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

        super.setTarget(pLivingEntity);
    }

    @Override
    public void tick() {
        this.setPersistenceRequired();
        currentPose.putIfAbsent(this, Pose.STANDING);
        this.setPose(currentPose.get(this));

        if (!level.isClientSide) {
            this.targetMap.forEach(livingEntity -> {
                if (livingEntity != null) {
                    if (livingEntity.isDeadOrDying()) { // Do stuff on target death
                        this.playSound(this.getKillSound(), 1.0F, 1.0F);
                        this.targetMap.remove(livingEntity);
                    }
                }
            });

            if (this.targetMap.isEmpty() && this.hasHadTarget()) { // Do stuff when all targets are dead.
                this.setCanTrigger(false);
                this.setHasHadTarget(false);
                this.setChargeTime(defaultChargeTime);
                RunnableCooldownHandler.addDelayedRunnable(() -> {
                    if (!this.isDeadOrDying()) {
                        this.setCanTrigger(true);
                    }
                }, 600);
            }
        }
        super.tick();
    }

    //Data Stuff

    public boolean isTriggered() {
        return this.entityData.get(DATA_ISTRIGGERED);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_ISTRIGGERED.equals(pKey) && this.hasBeenStaredAt() && this.level.isClientSide) {
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
                if (this.scp096.isLookingAtMe(entity) && this.scp096.canTrigger()) {

                    if (entity instanceof Player player) {
                        if (player.isCreative()) {
                            return false;
                        }
                    }

                    this.scp096.targetMap.add(entity);
                }

                return scp096.isAngryAt(entity) || this.scp096.targetMap.contains(entity);
            };

            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
        }

        public boolean canUse() {
            this.pendingTarget = this.scp096.level.getNearestPlayer(this.startAggroTargetConditions, this.scp096); // TODO change to LivingEntity
            if (!this.scp096.targetMap.isEmpty()) {
                if (this.scp096.getChargeTime() >= 0) {
                    this.scp096.setChargeTime(this.scp096.getChargeTime() - 2); // Have to remove 2 instead of 1 because this only fires every 2 ticks.
                    ServerLevel serverLevel = (ServerLevel) this.scp096.level;
                    serverLevel.sendParticles(ParticleTypes.RAIN, this.scp096.position().x, this.scp096.getEyeY(), this.scp096.position().z, 1, -0.2F, -0.1F, 0.0F, 1);
                    if (this.scp096.getChargeTime() == this.scp096.defaultChargeTime - 2) {
                        this.scp096.playSound(this.scp096.getTriggeredSound(), 1.0F, 1.0F);
                    }
                }
            }
            return !this.scp096.targetMap.isEmpty() && this.scp096.getChargeTime() <= 0;
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null && this.scp096.getChargeTime() <= 0) {
                if (!this.isAngerInducing.test(this.pendingTarget)) {
                    return false;
                } else {
                    this.scp096.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.target != null && this.continueAggroTargetConditions.test(this.scp096, this.target) || super.canContinueToUse();
            }
        }

        public void start() {
            this.scp096.setBeingStaredAt();
        }

        /**
         * Called Every Tick
         */
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
    }
}

