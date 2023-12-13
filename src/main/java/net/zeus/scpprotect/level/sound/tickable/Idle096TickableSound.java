package net.zeus.scpprotect.level.sound.tickable;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.zeus.scpprotect.level.entity.custom.SCP096;
import net.zeus.scpprotect.level.sound.ModSounds;

public class Idle096TickableSound extends AbstractTickableSoundInstance {

    private final SCP096 scp096;
    public boolean isPlaying = false;
    public Idle096TickableSound(SCP096 scp096) {
        super(ModSounds.SCP_096_IDLE.get(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.scp096 = scp096;
        this.looping = true;
        this.x = ((float)scp096.getX());
        this.y = ((float)scp096.getY());
        this.z = ((float)scp096.getZ());
    }
    @Override
    public boolean canPlaySound() {
        return this.scp096.getChargeTime() == this.scp096.defaultChargeTime;
    }

    @Override
    public void tick() {
        if (this.scp096.isRemoved() || !(this.scp096.getChargeTime() == this.scp096.defaultChargeTime)) {
            this.stop();
            this.isPlaying = false;
        } else {
            this.isPlaying = true;
            this.x = ((float) this.scp096.getX());
            this.y = ((float) this.scp096.getY());
            this.z = ((float) this.scp096.getZ());
        }
    }
}
