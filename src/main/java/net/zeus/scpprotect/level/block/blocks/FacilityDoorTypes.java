package net.zeus.scpprotect.level.block.blocks;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.zeus.scpprotect.level.sound.SCPSounds;

import java.util.Set;
import java.util.stream.Stream;

public record FacilityDoorTypes(String name, boolean canOpenByHand, SoundType soundType, SoundEvent doorClose, SoundEvent doorOpen) {
    private static final Set<FacilityDoorTypes> VALUES = new ObjectArraySet();
    public static final FacilityDoorTypes ENTRANCE;
    public static final FacilityDoorTypes LIGHT;
    public static final FacilityDoorTypes HEAVY;

    public FacilityDoorTypes(String pName) {
        this(pName, true, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN);
    }

    public FacilityDoorTypes(String name, boolean canOpenByHand, SoundType soundType, SoundEvent doorClose, SoundEvent doorOpen) {
        this.name = name;
        this.canOpenByHand = canOpenByHand;
        this.soundType = soundType;
        this.doorClose = doorClose;
        this.doorOpen = doorOpen;
    }

    public static FacilityDoorTypes register(FacilityDoorTypes pValue) {
        VALUES.add(pValue);
        return pValue;
    }

    public static Stream<FacilityDoorTypes> values() {
        return VALUES.stream();
    }

    public String name() {
        return this.name;
    }

    public boolean canOpenByHand() {
        return this.canOpenByHand;
    }

    public SoundType soundType() {
        return this.soundType;
    }

    public SoundEvent doorClose() {
        return this.doorClose;
    }

    public SoundEvent doorOpen() {
        return this.doorOpen;
    }
    
    static {
        ENTRANCE = register(new FacilityDoorTypes("entrance", false, SoundType.METAL, SCPSounds.EZ_DOOR_CLOSE.get(), SCPSounds.EZ_DOOR_OPEN.get()));
        LIGHT = register(new FacilityDoorTypes("light", false, SoundType.METAL, SCPSounds.LCZ_DOOR_CLOSE.get(), SCPSounds.LCZ_DOOR_OPEN.get()));
        HEAVY = register(new FacilityDoorTypes("heavy", false, SoundType.STONE, SCPSounds.HCZ_DOOR_CLOSE.get(), SCPSounds.HCZ_DOOR_OPEN.get()));
    }

}
