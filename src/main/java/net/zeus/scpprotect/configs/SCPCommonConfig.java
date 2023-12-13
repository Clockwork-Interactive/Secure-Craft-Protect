package net.zeus.scpprotect.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class SCPCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue SCP096Cooldown;

    static {
        BUILDER.push("SCP-096");
        SCP096Cooldown = BUILDER.comment("Should SCP-096 lay down after kill all targets?").define("Should:", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
