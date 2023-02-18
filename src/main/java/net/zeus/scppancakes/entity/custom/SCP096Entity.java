package net.zeus.scppancakes.entity.custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.custom.RunnableCooldownHandler;
import net.zeus.scppancakes.entity.ModEntity;
import net.zeus.scppancakes.event.CommonEvents;
import net.zeus.scppancakes.sound.ModSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import static net.zeus.scppancakes.event.CommonEvents.currentPose;

public class SCP096Entity extends Monster implements GeoEntity, NeutralMob {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E292A0");
    private float speedModifier = 0.33F;
    public static float HITBOX_WIDTH = 0.5F;
    public static float HITBOX_HEIGHT = 2.3F;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> DATA_ISTRIGGERED = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CHARGE_TIME = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CAN_TRIGGER = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_HAD_TARGET = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SITTING = SynchedEntityData.defineId(SCP096Entity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(0.7F, 1.3F);
    private final Set<LivingEntity> targetMap = new HashSet<>();
    private final int chargeTime = 700;
    private int targetChangeTime;

    public SCP096Entity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(0, new SCP096LookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.setPersistenceRequired();
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    protected SoundEvent getTriggeredSound() {
        return ModSounds.SCP_096_TRIGGERED.get();
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 99.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 700.0F)
                .add(Attributes.FOLLOW_RANGE, 1000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
    }

    public static final RawAnimation WALK096 = RawAnimation.begin().thenLoop("scp_096_walking");
    public static final RawAnimation RUN096 = RawAnimation.begin().thenLoop("scp_096_run");
    public static final RawAnimation IDLE096 = RawAnimation.begin().thenLoop("scp_096_sitting_idle");
    public static final RawAnimation TRIGGERED096 = RawAnimation.begin().thenLoop("scp_096_triggered");
    public static final RawAnimation LOSTTARGET096 = RawAnimation.begin().thenLoop("scp_096_lost_target");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 0, state -> {
            if (this.getChargeTime() != chargeTime && this.getChargeTime() != 0) {
                state.setAndContinue(TRIGGERED096);
                currentPose.put(this,Pose.STANDING);
            } else if (!this.canTrigger()) {
                state.setAndContinue(LOSTTARGET096);
                currentPose.put(this,Pose.STANDING);
            } else if (state.isMoving() && this.isTriggered()) {
                state.setAndContinue(RUN096);
                currentPose.put(this,Pose.STANDING);
            } else if (state.isMoving() && !this.isTriggered()) {
                state.setAndContinue(WALK096);
                currentPose.put(this,Pose.STANDING);
            } else if (!state.isMoving() && !this.isTriggered()) {
                state.setAndContinue(IDLE096);
                currentPose.put(this,Pose.SITTING);
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) { // Change hitboxes.
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
        this.entityData.define(DATA_CHARGE_TIME, chargeTime);
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
        currentPose.putIfAbsent(this,Pose.STANDING);
        this.setPose(currentPose.get(this));

        if (!level.isClientSide) {
            this.targetMap.forEach(livingEntity -> {
                if (livingEntity.isDeadOrDying()) {
                    this.targetMap.remove(livingEntity);
                }
            });

            if (this.targetMap.isEmpty() && this.hasHadTarget()) { // Do stuff when all targets are dead.
                this.setCanTrigger(false);
                this.setHasHadTarget(false);
                this.setChargeTime(chargeTime);
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
        private final SCP096Entity scp096;
        @Nullable
        private LivingEntity pendingTarget;
        private int aggroTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
        private final Predicate<LivingEntity> isAngerInducing;

        public SCP096LookForPlayerGoal(SCP096Entity scp096Entity, @Nullable Predicate<LivingEntity> pSelectionPredicate) {
            super(scp096Entity, Player.class, 0, false, false, pSelectionPredicate);
            this.scp096 = scp096Entity;

            this.isAngerInducing = (entity) -> {
                if (this.scp096.isLookingAtMe(entity) && this.scp096.canTrigger()) {

                    if (entity instanceof Player player) {
                        if (player.isCreative()) {
                            return false;
                        }
                    }

                    this.scp096.targetMap.add(entity);
                }

                return scp096Entity.isAngryAt(entity) || this.scp096.targetMap.contains(entity);
            };

            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
        }

        public boolean canUse() {
            this.pendingTarget = this.scp096.level.getNearestPlayer(this.startAggroTargetConditions, this.scp096); // TODO change to LivingEntity
            if (!this.scp096.targetMap.isEmpty()) {
                if (this.scp096.getChargeTime() != 0) {
                    this.scp096.setChargeTime(this.scp096.getChargeTime() - 2); // Have to remove 2 instead of 1 because this only fires every 2 ticks.
                    if (this.scp096.getChargeTime() == 698) {
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
