package net.zeus.scpprotect.mixin;


import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zeus.scpprotect.level.effect.SCPEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Shadow
    @Final
    public Player player;

    @Shadow
    public int selected;

    @Inject(at = @At("HEAD"), method = "getSelected", cancellable = true)
    public void selectInject(CallbackInfoReturnable<ItemStack> cir) {
        if (!this.player.level().isClientSide && this.player.hasEffect(SCPEffects.AMPUTATED.get())) {
            this.selected = 0;
            cir.setReturnValue(ItemStack.EMPTY);
        }
        if (this.player.level().isClientSide && !(this.player instanceof RemotePlayer) && this.player.hasEffect(SCPEffects.AMPUTATED.get())) {
            this.selected = 0;
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

}
