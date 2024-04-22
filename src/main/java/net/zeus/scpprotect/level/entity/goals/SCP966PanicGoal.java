package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.zeus.scpprotect.level.entity.entities.SCP966;

public class SCP966PanicGoal extends PanicGoal {
    public SCP966PanicGoal(SCP966 scp966, double pSpeedModifier) {
        super(scp966, pSpeedModifier);
    }

    @Override
    protected boolean shouldPanic() {
        return this.mob.isOnFire();
    }

    @Override
    public boolean canUse() {
        boolean returnValue = super.canUse();
        if (returnValue && this.mob instanceof SCP966 scp966) {
            scp966.setTarget(null);
        }
        return returnValue;
    }
}
