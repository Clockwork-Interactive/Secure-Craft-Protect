package net.zeus.scppancakes.client.models;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP173i;
import software.bernie.geckolib.model.GeoModel;

public class SCP173Model extends GeoModel<SCP173i> {

	@Override
	public ResourceLocation getModelResource(SCP173i animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"geo/scp_173i.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP173i animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"textures/entity/scp_173i_default_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP173i animatable) {
		return  new ResourceLocation(SCPPancakes.MOD_ID,"animations/animations_173i.animation.json");
	}
}