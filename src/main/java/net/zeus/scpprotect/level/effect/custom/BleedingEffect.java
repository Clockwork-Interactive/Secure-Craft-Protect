package net.zeus.scpprotect.level.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.level.particle.SCPParticles;
import net.zeus.scpprotect.util.Misc;
import net.zeus.scpprotect.util.ModDamageTypes;

public class BleedingEffect extends MobEffect {
    public BleedingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(Misc.damageSource(ModDamageTypes.BLEEDING, pLivingEntity.level()), pAmplifier > 6 ? 6 : 1.0F * pAmplifier + 1);
        pLivingEntity.level().addParticle(SCPParticles.BLOOD.get(), pLivingEntity.getRandomX(0.3D), pLivingEntity.getY(0.05D), pLivingEntity.getRandomZ(0.3D), 0, 0, 0);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
