package net.zeus.scpprotect.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.zeus.scpprotect.SCP;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> SCP939_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SCP.MOD_ID, "scp939_damage"));


    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SCP939_DAMAGE, new DamageType("scprotect.scp939_damage", 1.0F));
    }

}
