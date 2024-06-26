package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.level.entity.entities.SCP096;

import javax.annotation.Nullable;

public class WaterAvoiding096StrollGoal extends RandomStrollGoal {
    public static final float PROBABILITY = 0.00001F;
    private SCP096 scp096;
    protected final float probability;

    public WaterAvoiding096StrollGoal(SCP096 pMob, double pSpeedModifier) {
        this(pMob, pSpeedModifier, PROBABILITY);
        this.scp096 = pMob;
    }

    public WaterAvoiding096StrollGoal(SCP096 pMob, double pSpeedModifier, float pProbability) {
        super(pMob, pSpeedModifier);
        this.scp096 = pMob;
        this.probability = pProbability;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.scp096.getChargeTime() == this.scp096.getDefaultChargeTime() && !this.scp096.isTriggered()) {
            if (this.mob.level().getRawBrightness(this.mob.blockPosition(), 0) <= 10) {
                this.scp096.getEntityData().set(SCP096.SITTING, true);
                return null;
            } else {
                this.scp096.getEntityData().set(SCP096.SITTING, false);
                return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
            }
        }
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        }
        return null;
    }
}
