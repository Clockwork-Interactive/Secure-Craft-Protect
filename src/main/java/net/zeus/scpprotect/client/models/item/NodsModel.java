package net.zeus.scpprotect.client.models.item;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.armor.NightVisionGoggles;
import software.bernie.geckolib.model.GeoModel;

public class NodsModel extends GeoModel<NightVisionGoggles> {
    @Override
    public ResourceLocation getModelResource(NightVisionGoggles animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/armor/nods.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NightVisionGoggles animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/models/armor/night_vision_goggles.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NightVisionGoggles animatable) {
        return null;
    }
}
