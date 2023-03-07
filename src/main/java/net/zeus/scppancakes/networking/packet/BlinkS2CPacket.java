package net.zeus.scppancakes.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zeus.scppancakes.client.data.PlayerClientData;

import java.util.function.Supplier;

public class BlinkS2CPacket {

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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
                PlayerClientData.setBlink(blink));
    }

}
