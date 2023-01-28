package net.zeus.scppancakes.client.models;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP096Entity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.model.GeoModel;

public class SCP096Model extends GeoModel<SCP096Entity> {

	@Override
	public ResourceLocation getModelResource(SCP096Entity animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"geo/scp_096.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP096Entity animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"textures/entity/scp_096_calm_texture.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP096Entity animatable) {
		return new ResourceLocation(SCPPancakes.MOD_ID,"animations/walking_096.animation.json");
	}
}