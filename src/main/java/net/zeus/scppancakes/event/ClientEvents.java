package net.zeus.scppancakes.event;

import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.client.overlays.BlinkOverlay;
import net.zeus.scppancakes.client.renderer.SCP096EntityRenderer;
import net.zeus.scppancakes.client.renderer.SCP173EntityRenderer;
import net.zeus.scppancakes.entity.ModEntity;

@Mod.EventBusSubscriber(modid = SCPPancakes.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.SCP_096.get(), SCP096EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_173i.get(), SCP173EntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("blink_overlay", BlinkOverlay.BLINK_OVERLAY);
    }

}
