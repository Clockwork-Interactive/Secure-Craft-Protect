package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class WaterRandomLookAroundGoal extends RandomLookAroundGoal {

    private final Mob mob;

    public WaterRandomLookAroundGoal(Mob pMob) {
        super(pMob);
        this.mob = pMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.mob.isInWaterOrBubble();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.mob.isInWaterOrBubble();
    }
}
