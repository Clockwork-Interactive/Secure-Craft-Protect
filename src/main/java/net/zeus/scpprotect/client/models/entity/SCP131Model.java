package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import net.zeus.scpprotect.level.entity.entities.SCP131;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

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
}
