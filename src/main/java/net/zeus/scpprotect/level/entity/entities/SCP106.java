package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.refractionapi.refraction.runnable.RunnableCooldownHandler;
import net.refractionapi.refraction.sound.SoundUtil;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.capabilities.Capabilities;
import net.zeus.scpprotect.level.entity.goals.AnomalyWalkGoal;
import net.zeus.scpprotect.level.entity.goals.SCP106WalkThroughBlocksGoal;
import net.zeus.scpprotect.level.entity.goals.navigation.SCP106Navigation;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.level.worldgen.dimension.SCPDimensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Set;

public class SCP106 extends SCPEntity {

    private int unseenTicks = 0;
    private static final EntityDataAccessor<Integer> FROZEN_TICKS = SynchedEntityData.defineId(SCP106.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SCP106.class, EntityDataSerializers.INT);

    private static final RawAnimation FALLING = RawAnimation.begin().thenPlay("scp_106_teleport_fall");
    private static final RawAnimation RISE = RawAnimation.begin().thenPlay("scp_106_teleport_rise");

    private SCP106WalkThroughBlocksGoal walkGoal;

    public SCP106(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void onEffectAdded(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        this.removeAllEffects();
        super.onEffectAdded(pEffectInstance, pEntity);
    }

    @Override
    protected void registerGoals() {
        this.walkGoal = new SCP106WalkThroughBlocksGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, walkGoal);
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new AnomalyWalkGoal(this, 1.0F, () -> this.getFrozenTicks() <= 0, () -> this.getFrozenTicks() <= 0));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.ATTACK_DAMAGE, 20.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 200.0F)
                .add(Attributes.FOLLOW_RANGE, 70.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 13.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new SCP106Navigation(this, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, SCP106State.NORMAL.ordinal());
        this.entityData.define(FROZEN_TICKS, 0);
    }

    public SCP106State getState() {
        return SCP106State.values()[this.entityData.get(STATE)];
    }

    public void setState(SCP106State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public int getFrozenTicks() {
        return this.entityData.get(FROZEN_TICKS);
    }

    public void setFrozenTicks(int ticks) {
        this.entityData.set(FROZEN_TICKS, ticks);
    }

    @Override
    public void awardKillScore(Entity pKilled, int pScoreValue, DamageSource pSource) {
        this.playSound(SCPSounds.SCP_106_KILL.get(), 1.0F, 1.0F);
        super.awardKillScore(pKilled, pScoreValue, pSource);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;
        LivingEntity target = this.getTarget();
        if (target != null && this.unseenTicks >= 150) {
            this.unseenTicks = -1;
            this.setState(SCP106State.FALLING);
            this.setFrozenTicks(260);
            RunnableCooldownHandler.addDelayedRunnable(() -> {
                if (target == null || target.isDeadOrDying()) return;
                int y = target.getOnPos().getY() + 1;
                while (this.level().getBlockState(target.getOnPos().atY(y)).isAir()) {
                    y--;
                    if (y < this.level().getMinBuildHeight()) { // Eh, just in case
                        y = target.getOnPos().getY();
                        break;
                    }
                }
                this.teleportTo(target.getX(), y + 1, target.getZ());
                this.playSound(SCPSounds.SCP_106_TELEPORT.get(), 1.0F, 1.0F);
                this.unseenTicks = 0;
                this.setState(SCP106State.RISE);
            }, 130);
            RunnableCooldownHandler.addDelayedRunnable(() -> this.unseenTicks = 0, 260);
        } else if (target != null && !this.hasLineOfSight(target)) {
            this.unseenTicks++;
        } else {
            this.unseenTicks = 0;
        }
        if (this.getFrozenTicks() > 0) {
            this.getNavigation().stop();
            this.setFrozenTicks(this.getFrozenTicks() - 1);
        }
        if (!this.walkGoal.passed) {
            if (this.noPhysics) { // Used for going through blocks
                this.setNoGravity(true);
                Vec3 delta = this.getDeltaMovement();
                this.setDeltaMovement(delta.x, -delta.y, delta.z);
            }
        } else {
            this.setNoGravity(false);
            this.noPhysics = false;
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (this.getFrozenTicks() > 0) return false;
        if (pEntity instanceof ServerPlayer serverPlayer) {
            this.teleportTo(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()); // Prevents 106 from being stuck in a wall
            ServerLevel scp106Dim = serverPlayer.serverLevel().getServer().getLevel(SCPDimensions.SCP_106_LEVEL);
            if (scp106Dim == null) {
                throw new IllegalStateException("SCP-106 dimension is null");
            }
            if (scp106Dim.equals(serverPlayer.level())) {
                serverPlayer.kill();
                return true;
            }
            if (!this.level().isClientSide) {
                pEntity.getCapability(Capabilities.SCP_DATA).ifPresent(scp -> {
                    scp.scp106TakenDim = pEntity.level().dimension().location();
                    scp.scp106TakenPos = pEntity.blockPosition();
                });
                pEntity.teleportTo(scp106Dim, -0.5F, 2, -0.5F, Set.of(), pEntity.getYRot(), pEntity.getXRot());
                SCPAdvancements.grant(serverPlayer, SCPAdvancements.A_DECAYED_MARCH);
            }
            SoundUtil.playLocalSound(serverPlayer, SCPSounds.POCKET_DIMENSION_ENTER.get());
            return false;
        } else if (!pEntity.level().isClientSide) {
            pEntity.kill();
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean doAnyAnimations(AnimationState<?> state) {
        return this.getFrozenTicks() <= 0;
    }

    @Override
    public boolean canContain() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SCPSounds.SCP_106_AMBIENT.get();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return pSource.is(DamageTypes.GENERIC_KILL) && super.hurt(pSource, pAmount);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        this.addContinuous(0, "fall", FALLING, (state) -> this.getState() == SCP106State.FALLING);
        this.addContinuous(1, "rise", RISE, (state) -> this.getState() == SCP106State.RISE && this.getFrozenTicks() > 0);
        controllerRegistrar.add(new AnimationController<>(this, "controller", (state) -> PlayState.STOP)
                .triggerableAnim("rise", RISE)
                .triggerableAnim("fall", FALLING));
    }

    public enum SCP106State {
        FALLING,
        RISE,
        NORMAL
    }

}
