package net.zeus.scppancakes.entity.custom.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.zeus.scppancakes.entity.custom.SCP173i;

import javax.annotation.Nullable;

public class SCP173RandomStrollGoal extends RandomStrollGoal {

    public SCP173RandomStrollGoal(SCP173i pMob, double pSpeedModifier) {
        this(pMob, pSpeedModifier, 0.001F);
    }

    public SCP173RandomStrollGoal(SCP173i pMob, double pSpeedModifier, float pProbability) {
        super(pMob, pSpeedModifier);
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob instanceof SCP173i scp173i) {
            if (scp173i.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return scp173i.canMove && scp173i.getTarget() == null ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
            }
        }
        return super.getPosition();
    }
}
