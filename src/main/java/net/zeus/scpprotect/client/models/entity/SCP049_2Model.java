package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP049_2;

public class SCP049_2Model extends DefaultGeoBiPedalModel<SCP049_2> {
    @Override
    public ResourceLocation getModelResource(SCP049_2 scp0492) {
        return new ResourceLocation(SCP.MOD_ID, "geo/entity/scp_049_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SCP049_2 scp0492) {
        return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_049_2.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SCP049_2 scp0492) {
        return null;
    }
}
