package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.models.entity.DefaultGeoBiPedalModel;
import net.zeus.scpprotect.level.entity.custom.SCP966;

public class SCP966Model extends DefaultGeoBiPedalModel<SCP966> {

	@Override
	public ResourceLocation getModelResource(SCP966 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"geo/entity/scp_966.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP966 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_966.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP966 animatable) {
		return null;
	}

	@Override
	public RenderType getRenderType(SCP966 animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(texture, true);
	}

}