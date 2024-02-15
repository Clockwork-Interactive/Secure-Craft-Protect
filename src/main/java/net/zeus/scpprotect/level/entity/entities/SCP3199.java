package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SCP3199 extends Monster implements Anomaly, GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(SCP3199.class, net.minecraft.network.syncher.EntityDataSerializers.BOOLEAN);
    public static final int maxTime = 7200;
    public int timeRemaining = maxTime;
    public static final List<SoundEvent> idles = List.of(
            ModSounds.SCP_3199_IDLE_1.get(),
            ModSounds.SCP_3199_IDLE_2.get(),
            ModSounds.SCP_3199_IDLE_3.get(),
            ModSounds.SCP_3199_IDLE_4.get(),
            ModSounds.SCP_3199_IDLE_5.get(),
            ModSounds.SCP_3199_IDLE_6.get(),
            ModSounds.SCP_3199_IDLE_7.get(),
            ModSounds.SCP_3199_IDLE_8.get(),
            ModSounds.SCP_3199_IDLE_9.get(),
            ModSounds.SCP_3199_IDLE_10.get(),
            ModSounds.SCP_3199_IDLE_11.get(),
            ModSounds.SCP_3199_IDLE_12.get()
    );
    public static final List<SoundEvent> hurt = List.of(
            ModSounds.SCP_3199_HURT_1.get(),
            ModSounds.SCP_3199_HURT_2.get(),
            ModSounds.SCP_3199_HURT_3.get()
    );
    public static final List<SoundEvent> death = List.of(
            ModSounds.SCP_3199_DEATH_1.get(),
            ModSounds.SCP_3199_DEATH_2.get(),
            ModSounds.SCP_3199_DEATH_3.get()
    );
    public static final SoundEvent layEgg = ModSounds.SCP_3199_LAY.get();


    public SCP3199(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, this::canAttack));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ENRAGED, false);
    }

    public boolean isEnraged() {
        return this.entityData.get(ENRAGED);
    }

    public void setEnraged(boolean enraged) {
        this.entityData.set(ENRAGED, enraged);
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        this.setEnraged(pTarget != null);
        super.setTarget(pTarget);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return RefractionMisc.getRandom(idles);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        SoundEvent soundEvent = RefractionMisc.getRandom(hurt);
        if (soundEvent != null) {
            this.playSound(soundEvent, 1.0F, 1.0F);
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        SoundEvent soundEvent = RefractionMisc.getRandom(death);
        if (soundEvent != null) {
            this.playSound(soundEvent, 1.0F, 1.0F);
        }
        super.die(pDamageSource);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickEggs();
    }

    public void tickEggs() {
        if (this.timeRemaining > 0) {
            this.timeRemaining--;
        } else {
            this.layEgg();
        }
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
        SCP3199Egg scp3199Egg = new SCP3199Egg(ModEntity.SCP_3199_EGG.get(), this.level());
        scp3199Egg.setPos(this.getX(), this.getY(), this.getZ());
        this.level().addFreshEntity(scp3199Egg);

        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, this.level());
        areaEffectCloud.setPos(this.getX(), this.getY(), this.getZ());
        areaEffectCloud.setPotion(Potions.POISON);
        areaEffectCloud.setDuration(60);
        this.level().addFreshEntity(areaEffectCloud);

        this.playSound(layEgg, 1.0F, 1.0F);

    }


    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("TimeRemaining")) {
            this.timeRemaining = pCompound.getInt("TimeRemaining");
        }
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("TimeRemaining", this.timeRemaining);
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public boolean canAttack(LivingEntity pTarget) {
        return !(pTarget instanceof SCP3199Egg || pTarget instanceof SCP3199) && super.canAttack(pTarget);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
