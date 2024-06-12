package net.zeus.scpprotect.networking.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.network.NetworkEvent;
import net.refractionapi.refraction.networking.Packet;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class PlayLocalSeenSoundS2C extends Packet {

    public PlayLocalSeenSoundS2C() {

    }

    public PlayLocalSeenSoundS2C(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().getSoundManager().play(new EntityBoundSoundInstance(SCPSounds.SCP_096_SEEN.get(), SoundSource.AMBIENT, 1.0F, 1.0F, Minecraft.getInstance().player, RandomSource.create().nextLong()));
        });
        context.setPacketHandled(true);
    }

}
