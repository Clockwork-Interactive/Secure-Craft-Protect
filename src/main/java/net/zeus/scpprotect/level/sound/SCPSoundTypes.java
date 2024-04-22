package net.zeus.scpprotect.level.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.RegistryObject;

public class SCPSoundTypes extends SoundType {
    public static final SCPSoundTypes PD_BLOCK;

    public SCPSoundTypes(float pVolume, float pPitch, SoundEvent pBreakSound, SoundEvent pStepSound, SoundEvent pPlaceSound, SoundEvent pHitSound, SoundEvent pFallSound) {
        super(pVolume, pPitch, pBreakSound, pStepSound, pPlaceSound, pHitSound, pFallSound);
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    static {
        PD_BLOCK = new SCPSoundTypes(1.0F, 1.0F, SoundEvents.STONE_BREAK, SCPSounds.PD_WALK.get(), SoundEvents.STONE_PLACE, SoundEvents.STONE_HIT, SoundEvents.STONE_FALL);
    }
}
