package net.zeus.scpprotect.level.effect.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.level.particle.SCPParticles;

import java.util.List;

public class Amputated extends MobEffect {

    public Amputated(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.getEffect(this).getDuration() % 2 == 0) {
            if (pLivingEntity.level() instanceof ServerLevel serverLevel) {
                Vec3 vec30 = pLivingEntity.getEyePosition();
                Vec3 vec3 = vec30.add(Vec3Helper.calculateViewVector(0.0F, pLivingEntity.getYRot() + 90.0F).scale(0.35F));
                Vec3 vec31 = vec30.add(Vec3Helper.calculateViewVector(0.0F, pLivingEntity.getYRot() - 90.0F).scale(0.35F));
                serverLevel.sendParticles(SCPParticles.BLOOD.get(), vec3.x, pLivingEntity.getY() + (pLivingEntity.isCrouching() ? 1.0F : 1.3F), vec3.z, 1, 0, 0, 0.1, 0.5);
                serverLevel.sendParticles(SCPParticles.BLOOD.get(), vec31.x, pLivingEntity.getY() + (pLivingEntity.isCrouching() ? 1.0F : 1.3F), vec31.z, 1, 0, 0, 0.1, 0.5);
            }
        }
        if (pLivingEntity.getEffect(this).getDuration() - 1 <= 0) {
            pLivingEntity.kill();
            return;
        }
        if (pLivingEntity.level().isClientSide) {
            Minecraft.getInstance().gameRenderer.setRenderHand(false);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }

}
