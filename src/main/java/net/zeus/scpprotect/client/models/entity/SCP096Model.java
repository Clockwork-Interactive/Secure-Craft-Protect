package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.level.sound.tickable.Idle096TickableSound;
import net.zeus.scpprotect.level.sound.tickable.PlayableTickableSound;
import net.zeus.scpprotect.level.sound.tickable.Running096TickableSound;

import java.util.HashMap;

public class SCP096Model extends DefaultGeoBiPedalModel<SCP096> {
    public HashMap<SCP096, Running096TickableSound> SOUNDS = new HashMap<>();

    @Override
    public String model(int process, SCP096 animatable) {
        Player player = Minecraft.getInstance().player;
        if (player == null || animatable == null) return "scp_096";
        if (animatable.isTriggered() && animatable.isAlive() && animatable.distanceTo(player) <= 32.0F) {
            if (!this.SOUNDS.containsKey(animatable)) {
                this.SOUNDS.put(animatable, new Running096TickableSound(animatable));
            }
            Running096TickableSound tickableSoundRunning = this.SOUNDS.get(animatable);
            if (!tickableSoundRunning.isPlaying) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.getSoundManager().play(tickableSoundRunning);
                tickableSoundRunning.isPlaying = true;
            }
        } else {
            if (this.SOUNDS.containsKey(animatable) && this.SOUNDS.get(animatable).isPlaying) {
                Running096TickableSound tickableSoundRunning = this.SOUNDS.get(animatable);
                tickableSoundRunning.isPlaying = false;
                tickableSoundRunning.stopped = true;
                this.SOUNDS.remove(animatable);
            }
        }

        if (process == 2) {
            return animatable.isTriggered() || animatable.getChargeTime() <= 20 ? "scp_096_rage" : "scp_096";
        } else if (process == 1) {
            return animatable.isTriggered() || animatable.getChargeTime() <= 20 ? "scp_096_triggered" : "scp_096";
        }

        return "scp_096";
    }

    @Override
    public String type(SCP096 animatable) {
        return "entity";
    }

    @Override
    public boolean hasAnimation(SCP096 animatable) {
        return true;
    }


    @Override
    public boolean hasIdle() {
        return true;
    }

    @Override
    public PlayableTickableSound createIdle(SCP096 animatable) {
        return new Idle096TickableSound(animatable);
    }

    @Override
    public boolean canIdlePlay(SCP096 animatable) {
        return animatable.isDefaultCharge();
    }

}