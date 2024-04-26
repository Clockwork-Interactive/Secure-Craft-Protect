package net.zeus.scpprotect.level.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

public class SCPSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SCP.MOD_ID);

    // Entities
    public static final RegistryObject<SoundEvent> SCP_058_SPEAKING = registerSoundEvent("scp_058_dialog");
    public static final RegistryObject<SoundEvent> SCP_096_RUNNING = registerDistanceSoundEvent("scp_096_running", 20.0F);
    public static final RegistryObject<SoundEvent> SCP_096_KILL = registerSoundEvent("scp_096_kill");
    public static final RegistryObject<SoundEvent> SCP_096_IDLE = registerDistanceSoundEvent("scp_096_idle", 35.0F);
    public static final RegistryObject<SoundEvent> SCP_096_TRIGGERED = registerDistanceSoundEvent("scp_096_triggered", 35.0F);
    public static final RegistryObject<SoundEvent> SCP_096_SEEN = registerSoundEvent("scp_096_seen");
    public static final RegistryObject<SoundEvent> SCP_109_POUR = registerSoundEvent("scp_109_pour");
    public static final RegistryObject<SoundEvent> SCP_173_HORROR = registerSoundEvent("scp_173_horror");
    public static final RegistryObject<SoundEvent> SCP_173_STRANGLE_SEQUENCE = registerSoundEvent("scp_173_strangle_sequence");
    public static final RegistryObject<SoundEvent> SCP_173_STRANGLE_KILL = registerSoundEvent("scp_173_strangle_kill");
    public static final RegistryObject<SoundEvent> SCP_173_KILL = registerSoundEvent("scp_173_kill");
    public static final RegistryObject<SoundEvent> SCP_173_MOVE = registerSoundEvent("scp_173_move");
    public static final RegistryObject<SoundEvent> SCP_330_SEVER = registerSoundEvent("scp_330_sever");
    public static final RegistryObject<SoundEvent> SCP_939_ATTACK = registerSoundEvent("scp_939_attack");
    public static final RegistryObject<SoundEvent> SCP_939_BABY_IDLE = registerSoundEvent("scp_939_baby_idle");
    public static final RegistryObject<SoundEvent> SCP_939_HURT = registerSoundEvent("scp_939_hurt");
    public static final RegistryObject<SoundEvent> SCP_939_SCREECH = registerSoundEvent("scp_939_screech");
    public static final RegistryObject<SoundEvent> SCP_939_SPOT_TARGET = registerSoundEvent("scp_939_spot_target");
    public static final RegistryObject<SoundEvent> SCP_3199_DEATH = registerSoundEvent("scp_3199_death");
    public static final RegistryObject<SoundEvent> SCP_3199_HURT = registerSoundEvent("scp_3199_hurt");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE = registerSoundEvent("scp_3199_idle");
    public static final RegistryObject<SoundEvent> SCP_3199_LAY = registerSoundEvent("scp_3199_lay");
    public static final RegistryObject<SoundEvent> SCP_111_IDLE = registerSoundEvent("scp_111_idle");
    public static final RegistryObject<SoundEvent> SCP_131_IDLE = registerSoundEvent("scp_131_idle");
    public static final RegistryObject<SoundEvent> SCP_131_HURT = registerSoundEvent("scp_131_hurt");
    public static final RegistryObject<SoundEvent> SCP_966_DEATH = registerSoundEvent("scp_966_death");
    public static final RegistryObject<SoundEvent> SCP_966_IDLE = registerSoundEvent("scp_966_idle");
    public static final RegistryObject<SoundEvent> SCP_049_ATTACK_FAIL = registerSoundEvent("scp_049_attack_fail");
    public static final RegistryObject<SoundEvent> SCP_049_KILL = registerSoundEvent("scp_049_kill");
    public static final RegistryObject<SoundEvent> SCP_049_LOSE_TARGET = registerSoundEvent("scp_049_lose_target");
    public static final RegistryObject<SoundEvent> SCP_049_RESURRECT = registerSoundEvent("scp_049_resurrect");
    public static final RegistryObject<SoundEvent> SCP_049_SPOTTED_TARGET = registerSoundEvent("scp_049_spot_target");

    // Blocks
    public static final RegistryObject<SoundEvent> PD_WALK = registerSoundEvent("pocket_dimension_step");


    private static RegistryObject<SoundEvent> registerDistanceSoundEvent(String name, float distance) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(SCP.MOD_ID, name), distance));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SCP.MOD_ID, name)));
    }
}
