package net.zeus.scpprotect.client.data;

import net.minecraft.client.Minecraft;
import net.zeus.scpprotect.level.entity.entities.SCP058;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.level.entity.entities.SCP106;
import net.zeus.scpprotect.level.sound.tickable.Idle058TickableSound;
import net.zeus.scpprotect.level.sound.tickable.Idle096TickableSound;
import net.zeus.scpprotect.level.sound.tickable.Idle106TickableSound;

import java.awt.*;
import java.util.HashMap;

public class PlayerClientData {
    private static boolean blink;
    public static int vignetteTick = 0;
    public static int maxVignette = 100;
    public static boolean persistVignette = false;

    public static int fovTick = 0;
    public static int currentFov = Integer.MAX_VALUE;
    public static boolean pulse = false;

    public static Color color = Color.WHITE;

    public static void setBlink(boolean blink) {
        PlayerClientData.blink = blink;
    }

    public static boolean isBlinking() {
        return blink;
    }

    // Dedicated servers will crash otherwise
    public static Idle096TickableSound createIdle096(SCP096 scp096) {
        return new Idle096TickableSound(scp096);
    }

    public static Idle058TickableSound createIdle058(SCP058 scp058) {
        return new Idle058TickableSound(scp058);
    }

    public static Idle106TickableSound createIdle106(SCP106 scp106) {
        return new Idle106TickableSound(scp106);
    }

}
