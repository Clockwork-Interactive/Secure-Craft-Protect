package net.zeus.scpprotect.client.models.item;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.armor.Nods;
import software.bernie.geckolib.model.GeoModel;

public class NodsModel extends GeoModel<Nods> {
    @Override
    public ResourceLocation getModelResource(Nods animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/nods.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Nods animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/models/armor/nods.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Nods animatable) {
        return null;
    }
}
