package net.zeus.scpprotect.client.models.entity;

import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.level.entity.entities.SCP106;
import net.zeus.scpprotect.level.sound.tickable.Idle106TickableSound;
import net.zeus.scpprotect.level.sound.tickable.PlayableTickableSound;

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

    @Override
    public boolean hasIdle() {
        return true;
    }

    @Override
    public PlayableTickableSound createIdle(SCP106 animatable) {
        return new Idle106TickableSound(animatable);
    }

}
