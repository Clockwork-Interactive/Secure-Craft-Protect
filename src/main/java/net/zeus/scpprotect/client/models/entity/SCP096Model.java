package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.custom.SCP096;
import software.bernie.geckolib.model.GeoModel;

public class SCP096Model extends GeoModel<SCP096> {

	@Override
	public ResourceLocation getModelResource(SCP096 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"geo/entity/scp_096.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP096 animatable) {
		return animatable.isTriggered() ? new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_096_rage_texture.png") : new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_096_calm_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP096 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"animations/entity/animations_096.animation.json");
	}
}