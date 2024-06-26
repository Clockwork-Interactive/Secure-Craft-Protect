package net.zeus.scpprotect.level.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.effect.effects.*;

public class SCPEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SCP.MOD_ID);

    public static final RegistryObject<AmnesiaEffect> AMNESIA = MOB_EFFECTS.register("amnesia", () -> new AmnesiaEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> INSOMNIA = MOB_EFFECTS.register("insomnia", () -> new InsomniaEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> AMPUTATED = MOB_EFFECTS.register("amputated", () -> new AmputatedEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> UNEXTINGUISHABLE = MOB_EFFECTS.register("unextinguishable", () -> new UnextinguishableEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> ANXIOUS = MOB_EFFECTS.register("anxious", () -> new AnxiousEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> PESTILENCE = MOB_EFFECTS.register("pestilence", () -> new PestilenceEffect(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> PACIFICATION = MOB_EFFECTS.register("pacification", () -> new PacificationEffect(MobEffectCategory.BENEFICIAL, 11956459));
    public static final RegistryObject<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding", () -> new BleedingEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> CORROSION = MOB_EFFECTS.register("corrosion", () -> new CorrosionEffect(MobEffectCategory.HARMFUL, 0).addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -50, AttributeModifier.Operation.ADDITION));

}
