package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.custom.SCP173i;
import software.bernie.geckolib.model.GeoModel;

public class SCP173IModel extends GeoModel<SCP173i> {

	@Override
	public ResourceLocation getModelResource(SCP173i animatable) {
		return new ResourceLocation(SCP.MOD_ID,"geo/entity/scp_173i.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP173i animatable) {
		return new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_173i_default_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP173i animatable) {
		return  new ResourceLocation(SCP.MOD_ID,"animations/entity/animations_173i.animation.json");
	}
}