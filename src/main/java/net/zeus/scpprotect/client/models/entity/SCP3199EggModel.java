package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.SCP3199Egg;

public class SCP3199EggModel extends DefaultModel<SCP3199Egg> {
    @Override
    public String model(int process, SCP3199Egg animatable) {
        return "scp_3199_egg";
    }

    @Override
    public String type(SCP3199Egg animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP3199Egg animatable) {
        return false;
    }

}
