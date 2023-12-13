package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.models.entity.DefaultGeoTetrapodModel;
import net.zeus.scpprotect.level.entity.custom.SCP019_2;

public class SCP019_2Model extends DefaultGeoTetrapodModel<SCP019_2> {

    @Override
    public ResourceLocation getModelResource(SCP019_2 animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/entity/scp_019_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SCP019_2 animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_019_2.png");
    }

}