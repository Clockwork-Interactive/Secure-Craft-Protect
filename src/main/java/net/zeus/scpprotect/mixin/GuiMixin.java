package net.zeus.scpprotect.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.effect.SCPEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Unique private static final ResourceLocation CORROSION = new ResourceLocation(SCP.MOD_ID, "textures/gui/corrosion_hearts.png");

    @Inject(method = "renderHeart", at = @At("HEAD"), cancellable = true)
    private void renderHeart(GuiGraphics ctx, Gui.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!blinking && type == Gui.HeartType.NORMAL && Minecraft.getInstance().cameraEntity instanceof Player player
                && (player.hasEffect(SCPEffects.CORROSION.get()))) {
            ResourceLocation identifier;
            if (player.hasEffect(SCPEffects.CORROSION.get())) {
                identifier = CORROSION;
            } else {
                return;
            }
            ctx.blit(identifier, x, y, halfHeart ? 9 : 0, v, 9, 9);
            ci.cancel();
        }
    }
}
