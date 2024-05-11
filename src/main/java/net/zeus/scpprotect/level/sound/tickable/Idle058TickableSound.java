package net.zeus.scpprotect.level.sound.tickable;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.zeus.scpprotect.level.entity.entities.SCP058;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class Idle058TickableSound extends PlayableTickableSound {

    private final SCP058 scp058;

    public Idle058TickableSound(SCP058 scp058) {
        super(SCPSounds.SCP_058_SPEAKING.get(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.scp058 = scp058;
        this.looping = true;
        this.x = ((float)scp058.getX());
        this.y = ((float)scp058.getY());
        this.z = ((float)scp058.getZ());
    }
    @Override
    public boolean canPlaySound() {
        return !this.scp058.isDeadOrDying();
    }

    @Override
    public void tick() {
        if (this.scp058.isRemoved()) {
            this.stop();
            this.isPlaying = false;
        } else {
            this.isPlaying = true;
            this.x = ((float) this.scp058.getX());
            this.y = ((float) this.scp058.getY());
            this.z = ((float) this.scp058.getZ());
        }
    }
}
