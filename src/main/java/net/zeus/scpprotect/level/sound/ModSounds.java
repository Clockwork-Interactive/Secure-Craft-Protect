package net.zeus.scpprotect.level.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SCP.MOD_ID);

    public static final RegistryObject<SoundEvent> SCP_096_RUNNING = registerDistanceSoundEvent("scp_096_running", 20.0F);
    public static final RegistryObject<SoundEvent> SCP_096_KILL = registerSoundEvent("scp_096_kill");
    public static final RegistryObject<SoundEvent> SCP_096_IDLE = registerDistanceSoundEvent("scp_096_idle", 35.0F);
    public static final RegistryObject<SoundEvent> SCP_096_TRIGGERED = registerDistanceSoundEvent("scp_096_triggered", 35.0F);
    public static final RegistryObject<SoundEvent> SCP_096_SEEN = registerSoundEvent("scp_096_seen");
    public static final RegistryObject<SoundEvent> SCP_109_POUR1 = registerSoundEvent("scp_109_pour1");
    public static final RegistryObject<SoundEvent> SCP_109_POUR2 = registerSoundEvent("scp_109_pour2");
    public static final RegistryObject<SoundEvent> SCP_109_POUR3 = registerSoundEvent("scp_109_pour3");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR_1 = registerSoundEvent("scp_173_horror_1");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR_2 = registerSoundEvent("scp_173_horror_2");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR_3 = registerSoundEvent("scp_173_horror_3");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR_4 = registerSoundEvent("scp_173_horror_4");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR_5 = registerSoundEvent("scp_173_horror_5");
    public static final RegistryObject<SoundEvent> SCP_173_STRANGLE_SEQUENCE = registerSoundEvent("scp_173_strangle_sequence");
    public static final RegistryObject<SoundEvent> SCP_173_STRANGLE_KILL = registerSoundEvent("scp_173_strangle_kill");
    public static final RegistryObject<SoundEvent> SCP_173_KILL_1 = registerSoundEvent("scp_173_kill_1");
    public static final RegistryObject<SoundEvent> SCP_173_KILL_2 = registerSoundEvent("scp_173_kill_2");
    public static final RegistryObject<SoundEvent> SCP_173_MOVE_1 = registerSoundEvent("scp_173_move_1");
    public static final RegistryObject<SoundEvent> SCP_173_MOVE_2 = registerSoundEvent("scp_173_move_2");
    public static final RegistryObject<SoundEvent> SCP_173_MOVE_3 = registerSoundEvent("scp_173_move_3");
    public static final RegistryObject<SoundEvent> SCP_330_SEVER = registerSoundEvent("scp_330_sever");


    private static RegistryObject<SoundEvent> registerDistanceSoundEvent(String name, float distance) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(SCP.MOD_ID, name), distance));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SCP.MOD_ID, name)));
    }

}
