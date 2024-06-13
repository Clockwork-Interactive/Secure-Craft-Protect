package net.zeus.scpprotect.client.data;

import java.awt.*;

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

}
