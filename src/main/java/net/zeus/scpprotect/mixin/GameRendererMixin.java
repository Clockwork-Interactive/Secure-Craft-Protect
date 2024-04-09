package net.zeus.scpprotect.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.level.item.SCPItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(at = @At("HEAD"), method = "getNightVisionScale", cancellable = true)
    private static void scaleInject(LivingEntity pLivingEntity, float pNanoTime, CallbackInfoReturnable<Float> cir) {
        if (pLivingEntity.getItemBySlot(EquipmentSlot.HEAD).is(SCPItems.NODS.get())) {
            cir.setReturnValue(1.0F);
        }
    }

}
