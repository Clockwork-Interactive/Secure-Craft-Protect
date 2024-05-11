package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP131;
import net.zeus.scpprotect.util.Misc;
import software.bernie.geckolib.core.animation.AnimationState;

public class SCP131Model extends DefaultModel<SCP131> {
    @Override
    public String model(int process, SCP131 animatable) {
        return "scp_131";
    }

    @Override
    public String type(SCP131 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP131 animatable) {
        return false;
    }

    @Override
    public ResourceLocation getTextureResource(SCP131 animatable) {
        return switch (animatable.getVariant()) {
            default -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_131/scp_131_a.png");
            case 1 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_131/scp_131_b.png");
        };
    }

    @Override
    public void setCustomAnimations(SCP131 animatable, long instanceId, AnimationState<SCP131> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        rotateGeoHead("scp131", animationState);
    }

}
