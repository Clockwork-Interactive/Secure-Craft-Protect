package net.zeus.scpprotect.networking.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.refractionapi.refraction.networking.Packet;
import net.zeus.scpprotect.client.data.PlayerClientData;

import java.util.function.Supplier;

public class BlinkS2CPacket extends Packet {

    final boolean blink;

    public BlinkS2CPacket(boolean blink) {
        this.blink = blink;
    }

    public BlinkS2CPacket(FriendlyByteBuf buf) {
        blink = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(blink);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> PlayerClientData.setBlink(blink));
        context.setPacketHandled(true);
    }


}
