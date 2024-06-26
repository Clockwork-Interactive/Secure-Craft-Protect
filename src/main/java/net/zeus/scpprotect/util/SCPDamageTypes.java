package net.zeus.scpprotect.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.zeus.scpprotect.SCP;

public class SCPDamageTypes {
    public static final ResourceKey<DamageType> SCP_939_DAMAGE = register("scp_939_damage");
    public static final ResourceKey<DamageType> SCP_096_DAMAGE = register("scp_096_damage");
    public static final ResourceKey<DamageType> AMPUTATED = register("amputated");
    public static final ResourceKey<DamageType> BLEEDING = register("bleeding");
    public static final ResourceKey<DamageType> REBEL = register("rebel");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SCP_939_DAMAGE, new DamageType("scprotect.scp_939_damage", 1.0F));
        context.register(SCP_096_DAMAGE, new DamageType("scprotect.scp_096_damage", 1.0F));
        context.register(AMPUTATED, new DamageType("scprotect.amputated", 1.0F));
        context.register(BLEEDING, new DamageType("scprotect.bleeding", 1.0F));
        context.register(REBEL, new DamageType("scprotect.rebel", 1.0F));
    }

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SCP.MOD_ID, name));
    }
}
