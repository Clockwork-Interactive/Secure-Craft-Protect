package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.projectiles.ToxicSpit;

public class ToxicSpitModel extends DefaultModel<ToxicSpit> {

    @Override
    public String model(int process, ToxicSpit animatable) {
        return "toxic_spit";
    }

    @Override
    public String type(ToxicSpit animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(ToxicSpit animatable) {
        return false;
    }

}
