package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP173;

public class SCP173Model extends DefaultModel<SCP173> {
	@Override
	public String model(int process, SCP173 animatable) {
		return "scp_173" + animatable.get173Type().id;
	}

	@Override
	public String type(SCP173 animatable) {
		return "entity";
	}

	@Override
	public boolean hasAnimation(SCP173 animatable) {
		return animatable.get173Type().equals(SCP173.SCP173Types.ISOLATION);
	}

	@Override
	public ResourceLocation getTextureResource(SCP173 animatable) {
		return new ResourceLocation(SCP.MOD_ID, "textures/entity/scp_173/scp_173" + animatable.get173Type().getId() + ".png");
	}
}