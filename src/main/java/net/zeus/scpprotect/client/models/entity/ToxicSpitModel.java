package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.custom.projectiles.ToxicSpit;

public class ToxicSpitModel extends DefaultModel<ToxicSpit> {

    @Override
    public String model(int process) {
        return "toxic_spit";
    }

    @Override
    public String type() {
        return "entity";
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

}
