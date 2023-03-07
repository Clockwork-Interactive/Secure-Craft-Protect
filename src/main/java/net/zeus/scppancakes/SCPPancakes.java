package net.zeus.scppancakes;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zeus.scppancakes.block.ModBlocks;
import net.zeus.scppancakes.entity.ModEntity;
import net.zeus.scppancakes.item.ModItems;
import net.zeus.scppancakes.networking.ModMessages;
import net.zeus.scppancakes.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(SCPPancakes.MOD_ID)
public class SCPPancakes
{
    public static final String MOD_ID = "scppancakes";
    public static final Logger LOGGER = LogUtils.getLogger();
    public SCPPancakes()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntity.register(modEventBus);

        ModSounds.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCreativeModeTab);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }

    public void registerCreativeModeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(SCPPancakes.MOD_ID, "securecraftprotecttab"),
                builder -> builder.icon(() -> new ItemStack(Items.STICK))
                        .title(Component.translatable("creativemodetab.securecraftprotecttab")).displayItems((enabledFeatures, entries, operatorEnabled) -> {
                            entries.accept(ModBlocks.SCULPTURE_EXCREMENT.get());
                        }));
    }
}
