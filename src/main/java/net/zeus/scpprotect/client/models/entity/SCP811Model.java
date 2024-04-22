package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP811;

public class SCP811Model extends BiPedalModel<SCP811> {
    @Override
    public String model(int process, SCP811 animatable) {
        return "scp_811";
    }

    @Override
    public ResourceLocation getTextureResource(SCP811 animatable) {
        if (animatable.isTame()) {
            return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_811_tame.png");
        } else {
            return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_811.png");
        }
    }

    @Override
    public boolean hasAnimation() {
        return true;
    }
}
