package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.level.sound.tickable.Idle096TickableSound;
import net.zeus.scpprotect.level.sound.tickable.Running096TickableSound;

public class SCP096Model extends BiPedalModel<SCP096> {
    public Running096TickableSound tickableSoundRunning = null;

    @Override
    public String model(int process, SCP096 animatable) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return "scp_096";

        if (animatable.isTriggered() && animatable.isAlive() && animatable.distanceTo(player) < 30.0F) {
            if (this.tickableSoundRunning == null) {
                this.tickableSoundRunning = new Running096TickableSound(animatable);
            }
            if (!this.tickableSoundRunning.isPlaying) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.getSoundManager().play(this.tickableSoundRunning);
                this.tickableSoundRunning.isPlaying = true;
            }
        } else {
            if (this.tickableSoundRunning != null) {
                this.tickableSoundRunning.isPlaying = false;
                this.tickableSoundRunning.stopped = true;
                this.tickableSoundRunning = null;
            }
        }

        if (process == 2) {
            return animatable.isTriggered() ? "scp_096_rage" : "scp_096";
        } else if (process == 1) {
            return animatable.isTriggered() ? "scp_096_triggered" : "scp_096";
        }
        return "scp_096";
    }

    @Override
    public boolean hasAnimation() {
        return true;
    }

}