package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.level.entity.entities.SCP173;

import javax.annotation.Nullable;

public class SCP173RandomStrollGoal extends RandomStrollGoal {

    public SCP173RandomStrollGoal(SCP173 scp173, double pSpeedModifier) {
        super(scp173, pSpeedModifier);
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob instanceof SCP173 scp173) {
            if (scp173.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return scp173.getTarget() == null ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
            }
        }
        return super.getPosition();
    }
}
