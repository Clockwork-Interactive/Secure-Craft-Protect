package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP049_2;

public class SCP049_2Model extends DefaultGeoBiPedalModel<SCP049_2> {
    @Override
    public String model(int process, SCP049_2 animatable) {
        return "scp_049_2";
    }

    @Override
    public String type(SCP049_2 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP049_2 animatable) {
        return false;
    }
}
