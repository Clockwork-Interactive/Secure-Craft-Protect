package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.level.entity.misc.ContainmentBox;

public class ContainmentModel extends DefaultModel<ContainmentBox> {
    @Override
    public String model(int process, ContainmentBox animatable) {
        return "containment_box";
    }

    @Override
    public String type(ContainmentBox animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(ContainmentBox animatable) {
        return false;
    }
}
