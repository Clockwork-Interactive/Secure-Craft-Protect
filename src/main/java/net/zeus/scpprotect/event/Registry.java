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
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.particle.ModParticles;
import net.zeus.scpprotect.level.particle.custom.GenericParticle;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Registry {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.SCP_019_2.get(), SCP019_2EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_173i.get(), SCP173IEntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_096.get(), SCP096EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_939.get(), SCP939EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_966.get(), SCP966EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.SCP_811.get(), SCP811EntityRenderer::new);
        event.registerEntityRenderer(ModEntity.TOXIC_SPIT.get(), ToxicSpitRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("vignette_overlay_966", VignetteOverlay.VIGNETTE_OVERLAY);
        event.registerAboveAll("nods_overlay", NodsOverlay.NODS_OVERLAY);
        event.registerAboveAll("blink_overlay", BlinkOverlay.BLINK_OVERLAY);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BLOOD.get(), GenericParticle.Provider::new);
    }

}
