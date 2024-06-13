package net.zeus.scpprotect;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.refractionapi.refraction.quest.client.ClientQuestInfo;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.configs.SCPClientConfig;
import net.zeus.scpprotect.configs.SCPServerConfig;
import net.zeus.scpprotect.datagen.advancements.SCPCriteriaTriggers;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlockEntities;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.effect.SCPPotions;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.fluid.SCPFluidTypes;
import net.zeus.scpprotect.level.fluid.SCPFluids;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.particle.SCPParticles;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.level.tab.SCPTabs;
import net.zeus.scpprotect.networking.ModMessages;
import org.slf4j.Logger;

@Mod(SCP.MOD_ID)
public class SCP {
    public static final String MOD_ID = "scprotect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SCP() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SCPEntities.ENTITIES.register(modEventBus);
        SCPSounds.SOUND_EVENTS.register(modEventBus);
        SCPItems.ITEMS.register(modEventBus);
        SCPBlocks.BLOCKS.register(modEventBus);
        FacilityBlocks.BLOCKS.register(modEventBus);
        SCPBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        SCPEffects.MOB_EFFECTS.register(modEventBus);
        SCPTabs.TABS.register(modEventBus);
        SCPParticles.PARTICLES.register(modEventBus);
        SCPFluidTypes.FLUID_TYPES.register(modEventBus);
        SCPFluids.FLUIDS.register(modEventBus);
        SCPPotions.POTIONS.register(modEventBus);

        AnomalyRegistry.init();
        SCPCriteriaTriggers.init();

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SCPServerConfig.SPEC, "scprotect-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SCPClientConfig.SPEC, "scprotect-client.toml");

        ClientQuestInfo.setDefaultRenderer(false);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public enum SCPTypes {
        KETER("§4", Component.literal("Keter").withStyle(ChatFormatting.RED), SCPAdvancements.KETER_ADVANCEMENT),
        EUCLID("§6", Component.literal("Euclid").withStyle(ChatFormatting.YELLOW), SCPAdvancements.EUCLID_ADVANCEMENT),
        SAFE("§2", Component.literal("Safe").withStyle(ChatFormatting.GREEN), SCPAdvancements.SAFE_ADVANCEMENT),
        UNCLASSIFIED("§7", Component.literal("Unclassified").withStyle(ChatFormatting.GRAY), SCPAdvancements.SAFE_ADVANCEMENT);

        public final String colorModifier;
        public final Component component;
        public final ResourceLocation advancement;

        SCPTypes(String colorModifier, Component component, ResourceLocation advancement) {
            this.colorModifier = colorModifier;
            this.component = component;
            this.advancement = advancement;
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

}
