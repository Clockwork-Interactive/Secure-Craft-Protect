package net.zeus.scpprotect.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.zeus.scpprotect.level.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void setScreenInject(Screen pGuiScreen, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(ModEffects.AMPUTATED.get()) && pGuiScreen instanceof InventoryScreen) {
            ci.cancel();
        }
    }

}
