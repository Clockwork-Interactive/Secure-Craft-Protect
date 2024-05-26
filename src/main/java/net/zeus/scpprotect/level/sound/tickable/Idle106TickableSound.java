package net.zeus.scpprotect.level.sound.tickable;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.zeus.scpprotect.level.entity.entities.SCP106;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class Idle106TickableSound extends PlayableTickableSound {

    public SCP106 scp106;

    public Idle106TickableSound(SCP106 scp106) {
        super(SCPSounds.SCP_106_IDLE_LOOP.get(), SoundSource.AMBIENT, RandomSource.create());
        this.scp106 = scp106;
        this.looping = true;
        this.x = (float) scp106.getX();
        this.y = (float) scp106.getY();
        this.z = (float) scp106.getZ();
    }

    @Override
    public void tick() {
        if (this.scp106.isRemoved()) {
            this.stop();
        } else {
            this.x = (float) this.scp106.getX();
            this.y = (float) this.scp106.getY();
            this.z = (float) this.scp106.getZ();
        }
    }

}
