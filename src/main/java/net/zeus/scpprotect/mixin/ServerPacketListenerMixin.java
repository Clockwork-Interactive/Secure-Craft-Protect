package net.zeus.scpprotect.mixin;

import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import net.zeus.scpprotect.level.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPacketListenerMixin implements ServerPlayerConnection, TickablePacketListener, ServerGamePacketListener {

    @Shadow
    public ServerPlayer player;

    @Inject(at = @At("HEAD"), method = "handlePlayerCommand", cancellable = true)
    public void handleInject(ServerboundPlayerCommandPacket pPacket, CallbackInfo ci) {
        if (this.player.hasEffect(ModEffects.AMPUTATED.get()) && pPacket.getAction() == ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "handlePlayerAction", cancellable = true)
    public void handleActionInject(CallbackInfo ci) {
        if (this.player.hasEffect(ModEffects.AMPUTATED.get())) {
            ci.cancel();
        }
    }

}
