package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.client.models.entity.BiPedalModel;
import net.zeus.scpprotect.level.entity.custom.SCP811;

public class SCP811Model extends BiPedalModel<SCP811> {
    @Override
    public String model(int process) {
        return "scp_811";
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }
}
