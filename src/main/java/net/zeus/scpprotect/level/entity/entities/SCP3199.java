package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.entity.goals.WaterRandomLookAroundGoal;
import net.zeus.scpprotect.level.entity.goals.WaterSinkingRandomStroll;
import net.zeus.scpprotect.level.entity.misc.ContainmentBox;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP3199 extends SCPEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(SCP3199.class, net.minecraft.network.syncher.EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(SCP3199.class, net.minecraft.network.syncher.EntityDataSerializers.INT);
    public static final int maxTime = 7200;
    public int timeRemaining = maxTime;

    public SCP3199(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, this::canAttack));
        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterRandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterSinkingRandomStroll(this, 1.0D));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ENRAGED, false);
        this.entityData.define(AGE, -1);
    }

    public boolean isEnraged() {
        return this.entityData.get(ENRAGED);
    }

    public void setEnraged(boolean enraged) {
        this.entityData.set(ENRAGED, enraged);
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        if (this.isInWaterOrBubble()) {
            super.setTarget(null);
            this.setEnraged(false);
            return;
        }
        this.setEnraged(pTarget != null);
        super.setTarget(pTarget);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        if (this.isInWaterOrBubble()) {
            this.setTarget(null);
            return null;
        }
        return super.getTarget();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 8.0F)
                .add(Attributes.ATTACK_SPEED, 0.5F)
                .add(Attributes.MAX_HEALTH, 25.0F)
                .add(Attributes.FOLLOW_RANGE, 32.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2F);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_3199;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SCPSounds.SCP_3199_IDLE.get();
    }

    @Override
    public void die(DamageSource pDamageSource) {
        SoundEvent soundEvent = SCPSounds.SCP_3199_DEATH.get();
        if (soundEvent != null) {
            this.playSound(soundEvent, 1.0F, 1.0F);
        }
        super.die(pDamageSource);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SCPSounds.SCP_3199_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) return;

        if (!this.isBaby())
            this.tickEggs();

        if (this.getAge() >= 0) {
            this.setAge(this.getAge() - 1);
        }

    }

    public void tickEggs() {
        if (this.getBlockStateOn().is(Blocks.SLIME_BLOCK) ||
                this.isInWaterOrBubble()) return;
        if (this.timeRemaining > 0) {
            this.timeRemaining--;
        } else {
            this.layEgg();
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return !pSource.is(DamageTypes.DROWN) && super.hurt(pSource, pAmount);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return pEffectInstance.getEffect() != MobEffects.POISON && super.canBeAffected(pEffectInstance);
    }

    @Override
    public void onKillEntity(LivingEntity entity) {
        this.timeRemaining /= 2;
    }

    public void layEgg() {
        this.timeRemaining = maxTime;
        SCP3199Egg scp3199 = AnomalyRegistry.SCP_3199_EGG.create(this.level(), this.position());
        this.level().addFreshEntity(scp3199);

        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, this.level());
        areaEffectCloud.setPos(this.getX(), this.getY(), this.getZ());
        areaEffectCloud.setPotion(Potions.POISON);
        areaEffectCloud.setDuration(60);
        this.level().addFreshEntity(areaEffectCloud);

        this.playSound(SCPSounds.SCP_3199_LAY.get(), 1.0F, 1.0F);

    }


    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("TimeRemaining")) {
            this.timeRemaining = pCompound.getInt("TimeRemaining");
        }
        if (pCompound.contains("Age")) {
            this.setAge(pCompound.getInt("Age"));
        }
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("TimeRemaining", this.timeRemaining);
        pCompound.putInt("Age", this.getAge());
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public boolean canAttack(LivingEntity pTarget) {
        return !(pTarget instanceof SCP3199Egg || pTarget instanceof SCP3199 || pTarget instanceof ContainmentBox || pTarget instanceof ArmorStand) && super.canAttack(pTarget);
    }

    public int babyDuration() {
        return 3200;
    }

    public void setBaby() {
        this.entityData.set(AGE, this.babyDuration());
    }

    public void setAge(int age) {
        this.entityData.set(AGE, age);
    }

    public int getAge() {
        return this.entityData.get(AGE);
    }

    @Override
    public boolean isBaby() {
        return this.getAge() != -1;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
