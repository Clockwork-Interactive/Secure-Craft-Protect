package net.zeus.scpprotect.level.entity.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ToxicSpit extends AbstractArrow implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ToxicSpit(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ToxicSpit(EntityType<? extends AbstractArrow> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 7; ++i) {
                int pos = (serverLevel.getRandom().nextIntBetweenInclusive(0, 1) * 2) - 1;
                serverLevel.sendParticles(ParticleTypes.SNEEZE, this.getX() + ((double) i / 25) * pos, this.getY() + 0.1F, this.getZ() + (serverLevel.getRandom().nextFloat() > 0.7F ? ((double) i / 25) * pos : 0), 0, 0, 0.0F, 0.0F, 0.1F);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (pResult.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 1, false, true, true));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0, false, true, true));
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHit(HitResult pResult) {
        this.discard();
    }


    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
