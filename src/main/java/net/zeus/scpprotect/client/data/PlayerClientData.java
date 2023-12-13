package net.zeus.scpprotect.client.data;

public class PlayerClientData {
    private static boolean blink;
    public static int vignetteTick = 0;
    public static int maxVignette = 100;
    public static boolean persistVignette = false;

    public static void setBlink(boolean blink) {
        PlayerClientData.blink = blink;
    }

    public static boolean isBlinking() {
        return blink;
    }
}
