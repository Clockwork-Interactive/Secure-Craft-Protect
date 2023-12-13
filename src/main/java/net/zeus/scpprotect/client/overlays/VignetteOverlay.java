package net.zeus.scpprotect.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.data.PlayerClientData;

public class VignetteOverlay {

    public static final ResourceLocation VIGNETTE = new ResourceLocation(SCP.MOD_ID, "overlay/vignette.png");

    public static final IGuiOverlay VIGNETTE_OVERLAY =  ((gui, guiGraphics, partialTick, screenWidth, screenHeight)  -> {
        float alpha = 0.0F;

        if (PlayerClientData.vignetteTick > 0 || PlayerClientData.persistVignette) {
            if (PlayerClientData.vignetteTick == 0) {
                alpha = 1.0F;
            } else {
                alpha = Mth.lerp((float) PlayerClientData.vignetteTick / PlayerClientData.maxVignette, 1.0F, 0.0F);
            }
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, VIGNETTE);
        guiGraphics.blit(VIGNETTE, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    });


}
