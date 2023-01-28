package net.zeus.scppancakes.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.client.renderer.SCP096EntityRenderer;
import net.zeus.scppancakes.entity.ModEntity;

@Mod.EventBusSubscriber(modid = SCPPancakes.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.SCP_096.get(), SCP096EntityRenderer::new);
    }
}
