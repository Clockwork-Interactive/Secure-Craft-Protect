package net.zeus.scppancakes.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scppancakes.SCPPancakes;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SCPPancakes.MOD_ID);

    public static final RegistryObject<SoundEvent> SCP_096_RUNNING = registerSoundEvent("scp_096_running");
    public static final RegistryObject<SoundEvent> SCP_096_KILL = registerSoundEvent("scp_096_kill");
    public static final RegistryObject<SoundEvent> SCP_096_IDLE = registerSoundEvent("scp_096_idle");
    public static final RegistryObject<SoundEvent> SCP_096_TRIGGERED = registerDistanceSoundEvent("scp_096_triggered", 35.0F);


    private static RegistryObject<SoundEvent> registerDistanceSoundEvent(String name, float Distance) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(SCPPancakes.MOD_ID, name), Distance));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SCPPancakes.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
