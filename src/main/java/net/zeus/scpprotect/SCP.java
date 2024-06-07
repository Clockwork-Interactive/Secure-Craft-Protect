package net.zeus.scpprotect;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.refractionapi.refraction.quest.client.ClientQuestInfo;
import net.zeus.scpprotect.configs.SCPClientConfig;
import net.zeus.scpprotect.configs.SCPCommonConfig;
import net.zeus.scpprotect.datagen.advancements.SCPCriteriaTriggers;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.SCPBlockEntities;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.effect.SCPPotions;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.fluid.SCPFluidTypes;
import net.zeus.scpprotect.level.fluid.SCPFluids;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.scp.SCP500Bottle;
import net.zeus.scpprotect.level.particle.SCPParticles;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.level.tab.SCPTabs;
import net.zeus.scpprotect.level.worldgen.features.SCPConfiguredFeatures;
import net.zeus.scpprotect.level.worldgen.features.SCPPlacedFeatures;
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
        modEventBus.addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SCPCommonConfig.SPEC, "scprotect-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SCPClientConfig.SPEC, "scprotect-client.toml");

        ClientQuestInfo.setDefaultRenderer(false);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(SCPFluids.SOURCE_SCP_006.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(SCPFluids.FLOWING_SCP_006.get(), RenderType.translucent());

            ItemProperties.register(SCPItems.SCP_500_BOTTLE.get(),
                    new ResourceLocation(SCP.MOD_ID, "filled"), (p_174625_, p_174626_, p_174627_, p_174628_) -> {
                        return SCP500Bottle.getFullnessDisplay(p_174625_);
                    });
        });
    }


    public enum SCPTypes {
        KETER("§4", Component.literal("Keter").withStyle(ChatFormatting.RED)),
        EUCLID("§6", Component.literal("Euclid").withStyle(ChatFormatting.YELLOW)),
        SAFE("§2", Component.literal("Safe").withStyle(ChatFormatting.GREEN)),
        UNCLASSIFIED("§7", Component.literal("Unclassified").withStyle(ChatFormatting.GRAY))
        ;

        public final String colorModifier;
        public final Component component;

        SCPTypes(String colorModifier, Component component) {
            this.colorModifier = colorModifier;
            this.component = component;
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(Items.GLASS_BOTTLE),
                    Ingredient.of(SCPItems.LAVENDER.get()),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), SCPPotions.PACIFICATION.get()));
        });
    }
}
