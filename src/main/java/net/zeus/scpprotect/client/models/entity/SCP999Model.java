package net.zeus.scpprotect.client.models.entity;

import net.minecraft.util.Mth;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import net.zeus.scpprotect.level.entity.entities.SCP999;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

public class SCP999Model extends DefaultModel<SCP999> {
    @Override
    public String model(int process, SCP999 animatable) {
        return "scp_999";
    }

    @Override
    public String type(SCP999 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP999 animatable) {
        return true;
    }
}
