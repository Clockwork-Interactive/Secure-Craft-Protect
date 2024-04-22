package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.Rebel;

public class RebelModel extends DefaultGeoTetrapodModel<Rebel> {


    @Override
    public String id(int process) {
        return "rebel";
    }

    @Override
    public String path(int process) {
        return "entity";
    }

}
