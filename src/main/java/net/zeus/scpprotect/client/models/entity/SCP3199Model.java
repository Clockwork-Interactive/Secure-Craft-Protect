package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.entities.SCP3199;

public class SCP3199Model extends DefaultGeoBiPedalModel<SCP3199> {

    @Override
    public String model(int process, SCP3199 animatable) {
        if (process == 2) {
            return animatable.isEnraged() ? "scp_3199_angry" : "scp_3199";
        }
        return "scp_3199";
    }

    @Override
    public String type(SCP3199 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP3199 animatable) {
        return false;
    }

    @Override
    public float zOffLeftArm() {
        return -10.0F;
    }

    @Override
    public float zOffRightArm() {
        return 10.0F;
    }

}
