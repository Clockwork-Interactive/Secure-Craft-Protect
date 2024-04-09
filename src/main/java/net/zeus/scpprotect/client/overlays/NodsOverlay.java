package net.zeus.scpprotect.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.SCPItems;

public class NodsOverlay {

    public static final IGuiOverlay NODS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight)  -> {
        ItemStack stack = gui.getMinecraft().player.getItemBySlot(EquipmentSlot.HEAD);
        if (stack.getItem() != SCPItems.NODS.get()) return;

        gui.setupOverlayRenderState(true, false);
        renderTextureOverlay(guiGraphics, new ResourceLocation(SCP.MOD_ID, "overlay/nods_overlay.png"), 1.0F, screenWidth, screenHeight);
    });

    public static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation location, float alpha, int screenWidth, int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, location);
        guiGraphics.blit(location, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
