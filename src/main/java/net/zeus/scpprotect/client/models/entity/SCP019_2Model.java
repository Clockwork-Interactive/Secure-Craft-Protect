package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP019_2;

public class SCP019_2Model extends DefaultGeoTetrapodModel<SCP019_2> {

    @Override
    public String id(int process) {
        return "scp_019_2";
    }

    @Override
    public String path(int process) {
        return "entity";
    }

}