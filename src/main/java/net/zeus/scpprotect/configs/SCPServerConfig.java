package net.zeus.scpprotect.configs;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class SCPServerConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SCP_096_REACT_TO_ENTITIES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_SCPS;

    static {
        BUILDER.push("SCP-096");
        SCP_096_REACT_TO_ENTITIES = BUILDER.comment("Toggle 096 being able to be enraged by entities other than Players").define("096 React To Other Entities", false);
        BUILDER.pop();
        BUILDER.push("Reality Scanner");
        // Sorry Rebel :( -- Zeus
        BLACKLISTED_SCPS = BUILDER.comment("List of SCPs that Reality Scanner will not spawn").defineList("Blacklisted SCPs", List.of("rebel", "scp_3199_egg", "scp_049_2", "scp_019_2"), o -> o instanceof String);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
