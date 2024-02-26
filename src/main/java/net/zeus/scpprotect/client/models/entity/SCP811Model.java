package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.SCP811;

public class SCP811Model extends BiPedalModel<SCP811> {
    @Override
    public String model(int process, SCP811 animatable) {
        return "scp_811";
    }


    @Override
    public boolean hasAnimation() {
        return false;
    }
}
