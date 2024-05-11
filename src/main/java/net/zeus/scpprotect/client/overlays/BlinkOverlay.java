package net.zeus.scpprotect.client.overlays;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.refractionapi.refraction.client.rendering.RenderHelper;
import net.zeus.scpprotect.client.data.PlayerClientData;

import java.awt.*;

public class BlinkOverlay {
    static int time = -10;
    public static final IGuiOverlay BLINK_OVERLAY = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        boolean isBlinking = PlayerClientData.isBlinking();
        if (!isBlinking) return;
        final int easeFactor = 15; // (DONT GO ABOVE 15)
        int alpha = (int) Math.ceil(255 * Math.pow(Math.E, -(Math.pow(time, 2) / easeFactor)));
        int color = (alpha << 24);
        if (time != 20) {
            time++;
            RenderHelper.fill(poseStack.pose(), 0, 0, screenWidth, screenHeight, new Color(color));
        } else {
            time = -10;
            PlayerClientData.setBlink(false);
        }
    }));
}
