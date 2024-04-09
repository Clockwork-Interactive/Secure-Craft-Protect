package net.zeus.scpprotect.client.data;

import net.minecraft.client.Minecraft;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.level.sound.tickable.Idle096TickableSound;

import java.util.HashMap;

public class PlayerClientData {
    private static boolean blink;
    public static int vignetteTick = 0;
    public static int maxVignette = 100;
    public static boolean persistVignette = false;
    private static final HashMap<Integer, Idle096TickableSound> idleSounds = new HashMap<>(); // This is a bad implementation, too bad.

    public static int fovTick = 0;
    public static int currentFov = Integer.MAX_VALUE;
    public static boolean pulse = false;

    public static void setBlink(boolean blink) {
        PlayerClientData.blink = blink;
    }

    public static boolean isBlinking() {
        return blink;
    }

    public static void checkAndUpdateIdle(SCP096 scp096) {
        Idle096TickableSound tickableSoundIdle = idleSounds.get(scp096.getId());
        if (tickableSoundIdle == null && scp096.isDefaultCharge()) {
            tickableSoundIdle = new Idle096TickableSound(scp096);
            tickableSoundIdle.isPlaying = true;
            Minecraft.getInstance().getSoundManager().play(tickableSoundIdle);
            idleSounds.put(scp096.getId(), tickableSoundIdle);
        }
        if (tickableSoundIdle != null && !tickableSoundIdle.isPlaying) {
            idleSounds.remove(scp096.getId());
        }
    }

}
