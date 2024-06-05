package net.zeus.scpprotect.level.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.zeus.scpprotect.level.interfaces.DataGenObj;

public class AmnesiaEffect extends MobEffect implements DataGenObj {

    public AmnesiaEffect(MobEffectCategory pCategory, int pColor) {
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
