package net.zeus.scppancakes.client.models;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP939;
import software.bernie.geckolib.model.GeoModel;

public class SCP939Model extends GeoModel<SCP939> {

	@Override
	public ResourceLocation getModelResource(SCP939 animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"geo/scp_096.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP939 animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"textures/entity/scp_096_calm_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP939 animatable) {
		return  new ResourceLocation(SCPPancakes.MOD_ID,"animations/animations_096.animation.json");
	}
}