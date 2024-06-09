package net.zeus.scpprotect.level.sound.tickable;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.zeus.scpprotect.configs.SCPClientConfig;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class Idle096TickableSound extends PlayableTickableSound {

    private final SCP096 scp096;

    public Idle096TickableSound(SCP096 scp096) {
        super(SCPClientConfig.IDLE_2.get() ? SCPSounds.SCP_096_IDLE.get() : SCPSounds.SCP_096_IDLE_2.get(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.scp096 = scp096;
        this.looping = true;
        this.x = ((float)scp096.getX());
        this.y = ((float)scp096.getY());
        this.z = ((float)scp096.getZ());
    }
    @Override
    public boolean canPlaySound() {
        return this.scp096.getChargeTime() == this.scp096.getDefaultChargeTime();
    }

    @Override
    public void tick() {
        if (this.scp096.isRemoved()) {
            this.stop();
        } else {
            this.x = ((float) this.scp096.getX());
            this.y = ((float) this.scp096.getY());
            this.z = ((float) this.scp096.getZ());
        }
    }

}
