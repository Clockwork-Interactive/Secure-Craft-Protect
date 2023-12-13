package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.models.entity.DefaultGeoTetrapodModel;
import net.zeus.scpprotect.level.entity.custom.SCP939;

public class SCP939Model extends DefaultGeoTetrapodModel<SCP939> {

	@Override
	public ResourceLocation getModelResource(SCP939 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"geo/entity/scp_939.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP939 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_939.png");
	}

}