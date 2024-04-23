package net.zeus.scpprotect.level.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

public class SCPSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SCP.MOD_ID);

    public static final RegistryObject<SoundEvent> SCP_058_SPEAKING = registerSoundEvent("scp_058_dialog");
    public static final RegistryObject<SoundEvent> SCP_096_RUNNING = registerDistanceSoundEvent("scp_096_running", 20.0F);
    public static final RegistryObject<SoundEvent> SCP_096_KILL = registerSoundEvent("scp_096_kill");
    public static final RegistryObject<SoundEvent> SCP_096_IDLE = registerDistanceSoundEvent("scp_096_idle", 35.0F);
    public static final RegistryObject<SoundEvent> SCP_096_IDLE_2 = registerDistanceSoundEvent("scp_096_idle_2", 35.0F);
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
    public static final RegistryObject<SoundEvent> SCP_939_ATTACK_1 = registerSoundEvent("scp_939_attack_1");
    public static final RegistryObject<SoundEvent> SCP_939_ATTACK_2 = registerSoundEvent("scp_939_attack_2");
    public static final RegistryObject<SoundEvent> SCP_939_ATTACK_3 = registerSoundEvent("scp_939_attack_3");
    public static final RegistryObject<SoundEvent> SCP_939_ATTACK_4 = registerSoundEvent("scp_939_attack_4");
    public static final RegistryObject<SoundEvent> SCP_939_BABY_IDLE_1 = registerSoundEvent("scp_939_baby_idle_1");
    public static final RegistryObject<SoundEvent> SCP_939_BABY_IDLE_2 = registerSoundEvent("scp_939_baby_idle_2");
    public static final RegistryObject<SoundEvent> SCP_939_BABY_IDLE_3 = registerSoundEvent("scp_939_baby_idle_3");
    public static final RegistryObject<SoundEvent> SCP_939_HURT_1 = registerSoundEvent("scp_939_hurt_1");
    public static final RegistryObject<SoundEvent> SCP_939_HURT_2 = registerSoundEvent("scp_939_hurt_2");
    public static final RegistryObject<SoundEvent> SCP_939_SCREECH = registerSoundEvent("scp_939_screech");
    public static final RegistryObject<SoundEvent> SCP_939_SPOT_TARGET = registerSoundEvent("scp_939_spot_target");
    public static final RegistryObject<SoundEvent> SCP_3199_DEATH_1 = registerSoundEvent("scp_3199_death_1");
    public static final RegistryObject<SoundEvent> SCP_3199_DEATH_2 = registerSoundEvent("scp_3199_death_2");
    public static final RegistryObject<SoundEvent> SCP_3199_DEATH_3 = registerSoundEvent("scp_3199_death_3");
    public static final RegistryObject<SoundEvent> SCP_3199_HURT_1 = registerSoundEvent("scp_3199_hurt_1");
    public static final RegistryObject<SoundEvent> SCP_3199_HURT_2 = registerSoundEvent("scp_3199_hurt_2");
    public static final RegistryObject<SoundEvent> SCP_3199_HURT_3 = registerSoundEvent("scp_3199_hurt_3");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_1 = registerSoundEvent("scp_3199_idle_1");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_10 = registerSoundEvent("scp_3199_idle_10");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_11 = registerSoundEvent("scp_3199_idle_11");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_12 = registerSoundEvent("scp_3199_idle_12");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_2 = registerSoundEvent("scp_3199_idle_2");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_3 = registerSoundEvent("scp_3199_idle_3");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_4 = registerSoundEvent("scp_3199_idle_4");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_5 = registerSoundEvent("scp_3199_idle_5");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_6 = registerSoundEvent("scp_3199_idle_6");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_7 = registerSoundEvent("scp_3199_idle_7");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_8 = registerSoundEvent("scp_3199_idle_8");
    public static final RegistryObject<SoundEvent> SCP_3199_IDLE_9 = registerSoundEvent("scp_3199_idle_9");
    public static final RegistryObject<SoundEvent> SCP_3199_LAY = registerSoundEvent("scp_3199_lay");
    public static final RegistryObject<SoundEvent> SCP_111_IDLE_1 = registerSoundEvent("scp_111_idle_1");
    public static final RegistryObject<SoundEvent> SCP_111_IDLE_2 = registerSoundEvent("scp_111_idle_2");

    // Blocks
    public static final RegistryObject<SoundEvent> PD_WALK = registerSoundEvent("pocket_dimension_step");


    private static RegistryObject<SoundEvent> registerDistanceSoundEvent(String name, float distance) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(SCP.MOD_ID, name), distance));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SCP.MOD_ID, name)));
    }

}
