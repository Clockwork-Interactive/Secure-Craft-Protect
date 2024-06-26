package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.interfaces.DataGenObj;
import net.zeus.scpprotect.util.SCPDamageTypes;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Rebel extends Monster implements Anomaly, GeoEntity, DataGenObj {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Rebel(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 2.0F, true));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, (livingEntity -> livingEntity.getRandom().nextFloat() > 0.99F)));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 50.0F)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.MAX_HEALTH, 20000.0F)
                .add(Attributes.FOLLOW_RANGE, 64.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0F)
                .add(ForgeMod.SWIM_SPEED.get(), 2.5F)
                .add(ForgeMod.ENTITY_REACH.get(), 2.0F);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return pEntity.hurt(RefractionMisc.damageSource(SCPDamageTypes.REBEL, this.level()), 1000);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.UNDEFINED;
    }

    @Override
    public String customID() {
        return "Rebel";
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        super.hurt(pSource, pAmount);
        this.heal(pAmount);
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
