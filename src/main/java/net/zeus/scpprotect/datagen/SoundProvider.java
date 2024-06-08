package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class SoundProvider extends SoundDefinitionsProvider {
    protected SoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, SCP.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        // Blocks
        addSound(SCPSounds.PD_WALK, create("block/pocket_dimension/pocket_dimension_step"));
        addSound(SCPSounds.SCP_330_SEVER, create("block/scp_330/scp_330_sever"));

        // Items
        addSound(SCPSounds.SCP_109_POUR, create("item/scp_109/scp_109_pour1"), create("item/scp_109/scp_109_pour2"), create("item/scp_109/scp_109_pour3"));

        // Entities
        // SCP 049
        addSound(SCPSounds.SCP_049_ATTACK_FAIL, create("entity/scp_049/scp_049_attack_fail"));
        addSound(SCPSounds.SCP_049_KILL, create("entity/scp_049/scp_049_kill"));
        addSound(SCPSounds.SCP_049_LOSE_TARGET, create("entity/scp_049/scp_049_lose_target_1"), create("entity/scp_049/scp_049_lose_target_2"));
        addSound(SCPSounds.SCP_049_RESURRECT, create("entity/scp_049/scp_049_resurrect"));
        addSound(SCPSounds.SCP_049_SPOTTED_TARGET, create("entity/scp_049/scp_049_spot_target_1"), create("entity/scp_049/scp_049_spot_target_2"), create("entity/scp_049/scp_049_spot_target_3"), create("entity/scp_049/scp_049_spot_target_4"), create("entity/scp_049/scp_049_spot_target_5"));

        // SCP 049-2
        addSound(SCPSounds.SCP_049_2_IDLE, create("entity/scp_049_2/scp_049_2_idle_1"), create("entity/scp_049_2/scp_049_2_idle_2"), create("entity/scp_049_2/scp_049_2_idle_3"));
        addSound(SCPSounds.SCP_049_2_ATTACK, create("entity/scp_049_2/scp_049_2_attack"));

        // SCP 058
        addSound(SCPSounds.SCP_058_SPEAKING, create("entity/scp_058/scp_058_dialog"));

        // SCP 096
        addSound(SCPSounds.SCP_096_IDLE, create("entity/scp_096/scp_096_idle"));
        addSound(SCPSounds.SCP_096_IDLE_2, create("entity/scp_096/scp_096_idle_2"));
        addSound(SCPSounds.SCP_096_KILL, create("entity/scp_096/scp_096_kill"));
        addSound(SCPSounds.SCP_096_RUNNING, create("entity/scp_096/scp_096_running"));
        addSound(SCPSounds.SCP_096_SEEN, create("entity/scp_096/scp_096_seen"));
        addSound(SCPSounds.SCP_096_TRIGGERED, create("entity/scp_096/scp_096_triggered"));

        // SCP 111
        addSound(SCPSounds.SCP_111_IDLE, create("entity/scp_111/scp_111_idle_1"), create("entity/scp_111/scp_111_idle_2"));

        // SCP 131
        addSound(SCPSounds.SCP_131_HURT, create("entity/scp_131/scp_131_hurt"));
        addSound(SCPSounds.SCP_131_IDLE, create("entity/scp_131/scp_131_idle"));

        // SCP 173
        addSound(SCPSounds.SCP_173_HORROR, create("entity/scp_173/scp_173_horror_1"), create("entity/scp_173/scp_173_horror_2"), create("entity/scp_173/scp_173_horror_3"), create("entity/scp_173/scp_173_horror_4"), create("entity/scp_173/scp_173_horror_5"));
        addSound(SCPSounds.SCP_173_KILL, create("entity/scp_173/scp_173_kill_1"), create("entity/scp_173/scp_173_kill_2"));
        addSound(SCPSounds.SCP_173_MOVE, create("entity/scp_173/scp_173_move_1"), create("entity/scp_173/scp_173_move_2"), create("entity/scp_173/scp_173_move_3"));
        addSound(SCPSounds.SCP_173_STRANGLE_KILL, create("entity/scp_173/scp_173_strangle_kill"));
        addSound(SCPSounds.SCP_173_STRANGLE_SEQUENCE, create("entity/scp_173/scp_173_strangle_sequence"));

        // SCP 939
        addSound(SCPSounds.SCP_939_ATTACK, create("entity/scp_939/scp_939_attack_1"), create("entity/scp_939/scp_939_attack_2"), create("entity/scp_939/scp_939_attack_3"), create("entity/scp_939/scp_939_attack_4"));
        addSound(SCPSounds.SCP_939_BABY_IDLE, create("entity/scp_939/scp_939_baby_idle_1"), create("entity/scp_939/scp_939_baby_idle_2"), create("entity/scp_939/scp_939_baby_idle_3"));
        addSound(SCPSounds.SCP_939_HURT, create("entity/scp_939/scp_939_hurt_1"), create("entity/scp_939/scp_939_hurt_2"));
        addSound(SCPSounds.SCP_939_SCREECH, create("entity/scp_939/scp_939_screech"));
        addSound(SCPSounds.SCP_939_SPOT_TARGET, create("entity/scp_939/scp_939_spot_target"));

        // SCP 966
        addSound(SCPSounds.SCP_966_DEATH, create("entity/scp_966/scp_966_death"));
        addSound(SCPSounds.SCP_966_IDLE, create("entity/scp_966/scp_966_idle_1"), create("entity/scp_966/scp_966_idle_2"), create("entity/scp_966/scp_966_idle_3"));

        // SCP 3199
        addSound(SCPSounds.SCP_3199_DEATH, create("entity/scp_3199/scp_3199_death_1"), create("entity/scp_3199/scp_3199_death_2"), create("entity/scp_3199/scp_3199_death_3"));
        addSound(SCPSounds.SCP_3199_HURT, create("entity/scp_3199/scp_3199_hurt_1"), create("entity/scp_3199/scp_3199_hurt_2"), create("entity/scp_3199/scp_3199_hurt_3"));
        addSound(SCPSounds.SCP_3199_IDLE, create("entity/scp_3199/scp_3199_idle_1"), create("entity/scp_3199/scp_3199_idle_2"), create("entity/scp_3199/scp_3199_idle_3"), create("entity/scp_3199/scp_3199_idle_4"), create("entity/scp_3199/scp_3199_idle_5"), create("entity/scp_3199/scp_3199_idle_6"), create("entity/scp_3199/scp_3199_idle_7"), create("entity/scp_3199/scp_3199_idle_8"), create("entity/scp_3199/scp_3199_idle_9"), create("entity/scp_3199/scp_3199_idle_10"), create("entity/scp_3199/scp_3199_idle_11"), create("entity/scp_3199/scp_3199_idle_12"));
        addSound(SCPSounds.SCP_3199_LAY, create("entity/scp_3199/scp_3199_lay"));

        // SCP 106
        addSound(SCPSounds.SCP_106_IDLE_LOOP, create("entity/scp_106/scp_106_idle_1"));
        addSound(SCPSounds.SCP_106_AMBIENT, create("entity/scp_106/scp_106_idle_2"));
        addSound(SCPSounds.SCP_106_TELEPORT, create("entity/scp_106/scp_106_teleport"));
        addSound(SCPSounds.POCKET_DIMENSION_ENTER, create("entity/scp_106/pocket_dimension_enter"));
        addSound(SCPSounds.POCKET_DIMENSION_EXIT, create("entity/scp_106/pocket_dimension_exit"));
        addSound(SCPSounds.POCKET_DIMENSION_AMBIENCE, create("entity/scp_106/pocket_dimension_ambience"));
        addSound(SCPSounds.SCP_106_KILL, create("entity/scp_106/scp_106_kill"));

        // Doors

        addSound(SCPSounds.EZ_DOOR_CLOSE, create("block/doors/ez_door_close"));
        addSound(SCPSounds.EZ_DOOR_OPEN, create("block/doors/ez_door_open"));
        addSound(SCPSounds.LCZ_DOOR_CLOSE, create("block/doors/lcz_door_close"));
        addSound(SCPSounds.LCZ_DOOR_OPEN, create("block/doors/lcz_door_open"));
        addSound(SCPSounds.HCZ_DOOR_CLOSE, create("block/doors/hcz_door_close"));
        addSound(SCPSounds.HCZ_DOOR_OPEN, create("block/doors/hcz_door_open"));
    }


    // beautiful helper methods
    private void addSound(RegistryObject<SoundEvent> soundEvent, SoundDefinition.Sound... sounds) {
        SoundDefinition definition = SoundDefinition.definition();
        definition.with(sounds);
        add(soundEvent, definition);
    }

    private Sound create(String path) {
        return sound(new ResourceLocation(SCP.MOD_ID, path));
    }
}
