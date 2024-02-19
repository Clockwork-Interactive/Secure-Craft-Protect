package net.zeus.scpprotect.level.entity.entities.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class WaterSinkingRandomStroll extends WaterAvoidingRandomStrollGoal {

    public WaterSinkingRandomStroll(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.mob.isInWaterOrBubble();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.mob.isInWaterOrBubble();
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            return super.getPosition();
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
        }
    }

}
