package net.zeus.scpprotect.level.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.zeus.scpprotect.level.interfaces.DataGenObj;

public class Amnesia extends MobEffect implements DataGenObj {

    public Amnesia(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public String customID() {
        return "AMN-C227";
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
