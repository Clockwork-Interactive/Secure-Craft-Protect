package net.zeus.scppancakes.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.networking.packet.BlinkS2CPacket;
import net.zeus.scppancakes.networking.packet.PlayLocalSeenSoundS2C;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SCPPancakes.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(BlinkS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BlinkS2CPacket::new)
                .encoder(BlinkS2CPacket::toBytes)
                .consumerMainThread(BlinkS2CPacket::handle)
                .add();

        net.messageBuilder(PlayLocalSeenSoundS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayLocalSeenSoundS2C::new)
                .encoder(PlayLocalSeenSoundS2C::toBytes)
                .consumerMainThread(PlayLocalSeenSoundS2C::handle)
                .add();


    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
