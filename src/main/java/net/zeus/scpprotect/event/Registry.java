package net.zeus.scpprotect.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.overlays.BlinkOverlay;
import net.zeus.scpprotect.client.overlays.NodsOverlay;
import net.zeus.scpprotect.client.overlays.VignetteOverlay;
import net.zeus.scpprotect.client.renderer.entity.*;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.particle.SCPParticles;
import net.zeus.scpprotect.level.particle.custom.GenericParticle;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Registry {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SCPEntities.SCP_019_2.get(), SCP019_2Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_058.get(), SCP058Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_173.get(), SCP173Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_096.get(), SCP096Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_939.get(), SCP939Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_966.get(), SCP966Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_811.get(), SCP811Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_3199.get(), SCP3199Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_3199_EGG.get(), SCP3199EggRenderer::new);
        event.registerEntityRenderer(SCPEntities.TOXIC_SPIT.get(), ToxicSpitRenderer::new);
        event.registerEntityRenderer(SCPEntities.CONTAINMENT_BOX.get(), ContainmentRenderer::new);
        event.registerEntityRenderer(SCPEntities.REBEL.get(), RebelRenderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_111.get(), SCP111Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_131.get(), SCP131Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_346.get(), SCP346Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_999.get(), SCP999Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_049.get(), SCP049Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_049_2.get(), SCP049_2Renderer::new);
        event.registerEntityRenderer(SCPEntities.SCP_106.get(), SCP106Renderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("vignette_overlay_966", VignetteOverlay.VIGNETTE_OVERLAY);
        event.registerAboveAll("nods_overlay", NodsOverlay.NODS_OVERLAY);
        event.registerAboveAll("blink_overlay", BlinkOverlay.BLINK_OVERLAY);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SCPParticles.BLOOD.get(), GenericParticle.Provider::new);
    }

}
