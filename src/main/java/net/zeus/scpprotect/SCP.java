package net.zeus.scpprotect;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zeus.scpprotect.configs.SCPCommonConfig;
import net.zeus.scpprotect.level.block.ModBlocks;
import net.zeus.scpprotect.level.block.ModBlockEntities;
import net.zeus.scpprotect.level.effect.ModEffects;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.item.ModItems;
import net.zeus.scpprotect.level.particle.ModParticles;
import net.zeus.scpprotect.level.sound.ModSounds;
import net.zeus.scpprotect.level.tab.ModTabs;
import net.zeus.scpprotect.networking.ModMessages;
import org.slf4j.Logger;

@Mod(SCP.MOD_ID)
public class SCP {
    public static final String MOD_ID = "scprotect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SCP() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntity.ENTITIES.register(modEventBus);

        ModSounds.SOUND_EVENTS.register(modEventBus);

        ModItems.ITEMS.register(modEventBus);

        ModBlocks.BLOCKS.register(modEventBus);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ModEffects.MOB_EFFECTS.register(modEventBus);

        ModTabs.TABS.register(modEventBus);

        ModParticles.PARTICLES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SCPCommonConfig.SPEC, "scprotect-common.toml");
        
        MinecraftForge.EVENT_BUS.register(this);
    }


    public enum SCPTypes {
        KETER("§4"),
        EUCLID("§6"),
        SAFE("§2"),
        ;

        public String colorModifier;

        SCPTypes(String colorModifier) {
            this.colorModifier = colorModifier;
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }



}
