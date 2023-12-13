package net.zeus.scpprotect.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {

    @Inject(at = @At("HEAD"), method = "resetToggleKeys")
    private static void resetInject(CallbackInfo ci) {
        Minecraft.getInstance().gameRenderer.setRenderHand(true);
    }


}
