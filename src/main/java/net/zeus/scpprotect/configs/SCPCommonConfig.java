package net.zeus.scpprotect.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class SCPCommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SCP_096_REACT_TO_ENTITIES;

    static {
        BUILDER.push("SCP-096 Reacting to other entities");
        SCP_096_REACT_TO_ENTITIES = BUILDER.comment("Toggle 096 being able to be enraged by entities other than Players").define("096 React To Other Entities", false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
