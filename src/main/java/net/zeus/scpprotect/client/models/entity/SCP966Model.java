package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP966;

public class SCP966Model extends DefaultGeoBiPedalModel<SCP966> {

	@Override
	public String model(int process, SCP966 animatable) {
		return "scp_966";
	}

	@Override
	public String type(SCP966 animatable) {
		return "entity";
	}

	@Override
	public boolean hasAnimation(SCP966 animatable) {
		return false;
	}

	@Override
	public RenderType getRenderType(SCP966 animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(texture, true);
	}

}