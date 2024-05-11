package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.SCP811;

public class SCP811Model extends DefaultGeoBiPedalModel<SCP811> {
    @Override
    public String model(int process, SCP811 animatable) {
        if (process == 2)
            return animatable.isTame() ? "scp_811_tame" : "scp_811";
        return "scp_811";
    }

    @Override
    public String type(SCP811 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP811 animatable) {
        return true;
    }

}
