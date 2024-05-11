package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP049;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class SCP049Model extends DefaultGeoBiPedalModel<SCP049> {
    @Override
    public String model(int process, SCP049 animatable) {
        return "scp_049";
    }

    @Override
    public String type(SCP049 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP049 animatable) {
        return false;
    }

    @Override
    public void onAnimate(AnimationState<?> state, SCP049 animatable) {
        CoreGeoBone rightArm = getAnimationProcessor().getBone("rightArm");
        if (rightArm != null) {
            if (animatable.isAggressive()) {
                rightArm.setRotX(190.0F);
            } else {
                rightArm.setRotX(0.0F);
            }
        }
    }
}
