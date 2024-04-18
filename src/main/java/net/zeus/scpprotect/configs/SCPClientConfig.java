package net.zeus.scpprotect.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class SCPClientConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> IDLE_2;

    static {
        BUILDER.push("SCP-096");
        IDLE_2 = BUILDER.comment("Enable the second idle sound for SCP-096").define("Enable Second Idle", false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
