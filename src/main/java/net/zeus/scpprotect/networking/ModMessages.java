package net.zeus.scpprotect.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.refractionapi.refraction.networking.Packet;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.networking.S2C.BlinkS2CPacket;
import net.zeus.scpprotect.networking.S2C.PlayLocalSeenSoundS2C;
import net.zeus.scpprotect.networking.S2C.PlayLocalSoundS2C;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;

public class ModMessages {

    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SCP.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions((s) -> true).serverAcceptedVersions((s) -> true).simpleChannel();
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        registerPacket(BlinkS2CPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        registerPacket(PlayLocalSoundS2C.class, NetworkDirection.PLAY_TO_CLIENT);
        registerPacket(PlayLocalSeenSoundS2C.class, NetworkDirection.PLAY_TO_CLIENT);
        registerPacket(VignetteS2CPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    private static <P extends Packet> void registerPacket(Class<P> msgClass, NetworkDirection direction) {
        INSTANCE.messageBuilder(msgClass, id(), direction).decoder((byteBuf) -> {
                    try {
                        return msgClass.getConstructor(FriendlyByteBuf.class).newInstance(byteBuf);
                    } catch (Exception var3) {
                        throw new RuntimeException(var3);
                    }
                })
                .encoder(Packet::toBytes)
                .consumerMainThread((msg, supplier) -> msg.handle(supplier.get()))
                .add();
    }

}
