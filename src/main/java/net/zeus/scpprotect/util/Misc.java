package net.zeus.scpprotect.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Misc {
    static RandomSource random = RandomSource.create();

    public static void summonParticlesAroundEntity(Entity entity, ParticleOptions particleOptions, ServerLevel level, int ParticleAmount) {
        for (int i = 0; i < ParticleAmount; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            level.sendParticles(particleOptions, entity.getRandomX(1), entity.getRandomY() + 0.5D, entity.getRandomZ(1), ParticleAmount, d0, d1, d2, 0.0D);
        }
    }

    public static DamageSource damageSource(ResourceKey<DamageType> damageType, Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType));
    }

    public static Vec3 getEntityLookAtVec3(LivingEntity livingEntity, double range) {
        Vec3 vec3 = livingEntity.getEyePosition();
        Vec3 vec31 = calculateViewVector(livingEntity.getXRot(), livingEntity.getYRot()).scale(range);
        Vec3 vec32 = vec3.add(vec31);
        return vec32;
    }

    public static Vec3 calculateViewVector(float pXRot, float pYRot) {
        float f = pXRot * ((float) Math.PI / 180F);
        float f1 = -pYRot * ((float) Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((f3 * f4), (-f5), (f2 * f4));
    }

    public static void knockback(LivingEntity livingEntity, Player player, float strength) {
        livingEntity.knockback(strength, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(player.getYRot() * ((float) Math.PI / 180F))));
    }

}