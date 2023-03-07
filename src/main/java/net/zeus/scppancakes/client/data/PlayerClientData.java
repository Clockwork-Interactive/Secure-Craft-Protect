package net.zeus.scppancakes.client.data;

public class PlayerClientData {
    private static boolean blink;

    public static void setBlink(boolean blink) {
        PlayerClientData.blink = blink;
    }

    public static boolean isBlinking() {
        return blink;
    }
}
