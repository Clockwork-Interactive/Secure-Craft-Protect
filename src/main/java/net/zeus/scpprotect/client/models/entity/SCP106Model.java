package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.SCP106;

public class SCP106Model extends DefaultGeoBiPedalModel<SCP106> {

    @Override
    public String model(int process, SCP106 animatable) {
        return "scp_106";
    }

    @Override
    public String type(SCP106 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP106 animatable) {
        return true;
    }

}
