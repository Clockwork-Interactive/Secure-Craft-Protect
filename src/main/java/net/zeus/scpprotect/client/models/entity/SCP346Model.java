package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP346;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SCP346Model extends DefaultGeoBirdModel<SCP346> {
	@Override
	public ResourceLocation getModelResource(SCP346 scp346) {
		return new ResourceLocation(SCP.MOD_ID, "geo/entity/scp_346.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP346 scp346) {
		return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_346.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP346 scp346) {
		return new ResourceLocation(SCP.MOD_ID, "animations/entity/scp_346.animation.json");
	}
}