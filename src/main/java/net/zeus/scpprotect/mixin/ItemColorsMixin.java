package net.zeus.scpprotect.mixin;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.ItemStack;
import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.level.item.SCPItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public class ItemColorsMixin {

    @Inject(method = "getColor", at = @At("HEAD"), cancellable = true)
    private void getColor(ItemStack stack, int par2, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem().equals(SCPItems.ARROW.get())) {
            cir.setReturnValue(PlayerClientData.color.getRGB());
        }
    }

}
