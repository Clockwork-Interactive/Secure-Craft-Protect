package net.zeus.scppancakes.client.models;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP096;
import software.bernie.geckolib.model.GeoModel;

public class SCP096Model extends GeoModel<SCP096> {

	@Override
	public ResourceLocation getModelResource(SCP096 animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"geo/scp_096.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP096 animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"textures/entity/scp_096_calm_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP096 animatable) {
		return  new ResourceLocation(SCPPancakes.MOD_ID,"animations/animations_096.animation.json");
	}
}