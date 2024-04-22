package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP939;

public class SCP939Model extends DefaultGeoTetrapodModel<SCP939> {

	@Override
	public String id(int process) {
		return "scp_939";
	}

	@Override
	public String path(int process) {
		return "entity";
	}

}