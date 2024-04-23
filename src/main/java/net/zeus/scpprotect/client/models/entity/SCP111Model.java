package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

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
    public ResourceLocation getTextureResource(SCP111 animatable) {
        return switch (animatable.getVariant()) {
            default -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_red.png");
            case 1 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_blue.png");
            case 2 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_green.png");
            case 3 -> new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_111/scp_111_yellow.png");
        };
    }

    @Override
    public void setCustomAnimations(SCP111 animatable, long instanceId, AnimationState<SCP111> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData modelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(modelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(modelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
