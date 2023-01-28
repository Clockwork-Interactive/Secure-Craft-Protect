package net.zeus.scppancakes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zeus.scppancakes.entity.ModEntity;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(SCPPancakes.MOD_ID)
public class SCPPancakes
{
    public static final String MOD_ID = "scppancakes";
    private static final Logger LOGGER = LogUtils.getLogger();
    public SCPPancakes()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntity.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
}
