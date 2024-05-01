package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP049;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class SCP049Model extends DefaultGeoBiPedalModel<SCP049> {
    @Override
    public ResourceLocation getModelResource(SCP049 scp049) {
        return new ResourceLocation(SCP.MOD_ID, "geo/entity/scp_049.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SCP049 scp049) {
        return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_049.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SCP049 scp049) {
        return null;
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
