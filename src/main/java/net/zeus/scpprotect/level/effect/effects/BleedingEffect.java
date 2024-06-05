package net.zeus.scpprotect.level.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.refractionapi.refraction.math.MathHelper;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.level.particle.SCPParticles;
import net.zeus.scpprotect.util.SCPDamageTypes;

public class BleedingEffect extends MobEffect {
    public BleedingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(RefractionMisc.damageSource(SCPDamageTypes.BLEEDING, pLivingEntity.level()), pAmplifier > 6 ? 6 : 1.0F * pAmplifier + 1);
        for (int i = 0; i < (pAmplifier + 1) * 2; i++) {
            Vec3 vec3 = Vec3Helper.getVec(pLivingEntity, 0, pLivingEntity.yBodyRot,0.15F, 0.0F, -0.5F)
                    .add(pLivingEntity.getRandom().nextFloat() / 4 * MathHelper.getRandomOne(), pLivingEntity.getRandom().nextFloat() / 4 * MathHelper.getRandomOne(), pLivingEntity.getRandom().nextFloat() / 4 * MathHelper.getRandomOne());
            pLivingEntity.level().addParticle(SCPParticles.BLOOD.get(), vec3.x, vec3.y, vec3.z, 0, 0, 0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
