package net.zeus.scpprotect.level.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.util.Misc;

public class SCPPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, SCP.MOD_ID);

    public static final RegistryObject<Potion> PACIFICATION = POTIONS.register("pacification", () -> new Potion(new MobEffectInstance(SCPEffects.PACIFICATION.get(), Misc.TPS * 60)));
}
