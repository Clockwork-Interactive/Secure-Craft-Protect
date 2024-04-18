package net.zeus.scpprotect.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class SCPCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("SCP-096");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
