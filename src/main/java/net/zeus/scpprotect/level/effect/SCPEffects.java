package net.zeus.scpprotect.level.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.effect.custom.*;

public class SCPEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SCP.MOD_ID);

    public static final RegistryObject<AmnesiaEffect> AMNESIA = MOB_EFFECTS.register("amnesia", () -> new AmnesiaEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> INSOMNIA = MOB_EFFECTS.register("insomnia", () -> new InsomniaEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> AMPUTATED = MOB_EFFECTS.register("amputated", () -> new AmputatedEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> UNEXTINGUISHABLE = MOB_EFFECTS.register("unextinguishable", () -> new UnextinguishableEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> ANXIOUS = MOB_EFFECTS.register("anxious", () -> new AnxiousEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> PESTILENCE = MOB_EFFECTS.register("pestilence", () -> new PestilenceEffect(MobEffectCategory.HARMFUL, 0));

}
