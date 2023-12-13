package net.zeus.scpprotect.networking.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.refractionapi.refraction.networking.Packet;
import net.zeus.scpprotect.client.data.PlayerClientData;

import java.util.function.Supplier;

public class VignetteS2CPacket extends Packet {

    final int vignette;
    final boolean overwrite;
    final boolean persistVignette;

    public VignetteS2CPacket(int vignette, boolean overwrite, boolean persistVignette) {
        this.vignette = vignette;
        this.overwrite = overwrite;
        this.persistVignette = persistVignette;
    }

    public VignetteS2CPacket(FriendlyByteBuf buf) {
        vignette = buf.readInt();
        overwrite = buf.readBoolean();
        persistVignette = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(vignette);
        buf.writeBoolean(overwrite);
        buf.writeBoolean(persistVignette);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            if ((PlayerClientData.vignetteTick > 0 || PlayerClientData.persistVignette) && !this.overwrite) return;
            PlayerClientData.vignetteTick = this.vignette;
            PlayerClientData.maxVignette = this.vignette;
            PlayerClientData.persistVignette = this.persistVignette;
        });
        context.setPacketHandled(true);
    }

}
