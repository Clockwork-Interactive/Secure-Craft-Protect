package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import net.zeus.scpprotect.util.Misc;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimationState;

public class SCP111Model extends DefaultModel<SCP111> {
    @Override
    public String model(int process, SCP111 animatable) {
        return "scp_111";
    }

    @Override
    public String type(SCP111 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP111 animatable) {
        return false;
    }

    @Override
    public ResourceLocation getTextureResource(@NotNull SCP111 animatable) {
        return switch (animatable.getVariant()) {
            default -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_red.png");
            case 1 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_blue.png");
            case 2 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_green.png");
            case 3 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_yellow.png");
        };
    }

    @Override
    public void setCustomAnimations(SCP111 animatable, long instanceId, AnimationState<SCP111> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        rotateGeoHead("head", animationState);
    }

}
