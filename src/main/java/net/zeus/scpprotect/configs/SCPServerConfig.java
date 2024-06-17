package net.zeus.scpprotect.configs;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class SCPServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SCP_096_REACT_TO_ENTITIES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PINK_CANDY_FROM_SCP_330;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DOORS_TEMP_OPEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> BREAKING_BEDROCK_WITH_SCP_063;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_SCPS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DESTROYABLES;
    public static final List<Block> DESTROYABLE_BLOCKS = new ArrayList<>();

    static {
        BUILDER.push("SCP-096");
        SCP_096_REACT_TO_ENTITIES = BUILDER.comment("Toggle SCP-096 being triggered by other entities").define("SCP-096 React To Other Entities", false);
        DESTROYABLES = BUILDER.comment("Blocks that 096 can destroy").defineList("Destroyables", List.of(), o -> o instanceof String);
        BUILDER.pop();

        BUILDER.push("Reality Scanner");
        // Sorry Rebel :( -- Zeus
        BLACKLISTED_SCPS = BUILDER.comment("List of SCPs that Reality Scanner will not spawn").defineList("Blacklisted SCPs", List.of("rebel", "scp_3199_egg", "scp_049_2", "scp_019_2"), o -> o instanceof String);
        BUILDER.pop();

        BUILDER.push("Pink Candy");
        PINK_CANDY_FROM_SCP_330 = BUILDER.comment("Toggle pink candy being obtainable from SCP-330 (PLEASE TURN MOBGRIEFING OFF)").define("Pink candy from 330", false);
        BUILDER.pop();

        BUILDER.push("SCP-063 Breaking Bedrock");
        BREAKING_BEDROCK_WITH_SCP_063 = BUILDER.comment("Toggle SCP-063 breaking bedrock").define("Breaking Bedrock With SCP-063", false);
        BUILDER.pop();

        BUILDER.push("Doors Temp Open");
        DOORS_TEMP_OPEN = BUILDER.comment("Toggle doors staying open or automatically closing").define("Doors Temp Open", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
