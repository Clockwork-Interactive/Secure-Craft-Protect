package net.zeus.scpprotect.event;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.overlays.BlinkOverlay;
import net.zeus.scpprotect.client.overlays.NodsOverlay;
import net.zeus.scpprotect.client.overlays.VignetteOverlay;
import net.zeus.scpprotect.client.renderer.entity.*;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.effect.SCPPotions;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.fluid.SCPFluids;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.scp.SCP500Bottle;
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
    public static void setup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(SCPFluids.SOURCE_SCP_006.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(SCPFluids.FLOWING_SCP_006.get(), RenderType.translucent());

            PotionBrewing.addMix(Potions.AWKWARD, SCPBlocks.LAVENDER.get().asItem(), SCPPotions.PACIFICATION.get());

            ItemProperties.register(SCPItems.SCP_500_BOTTLE.get(),
                    new ResourceLocation(SCP.MOD_ID, "filled"), (pStack, pClientLevel, pLivingEntity, pId) -> SCP500Bottle.getFullnessDisplay(pStack));
            ItemProperties.register(SCPItems.SCP_207.get(),
                    new ResourceLocation(SCP.MOD_ID, "sips"), (pStack, pClientLevel, pLivingEntity, pId) -> pStack.getOrCreateTag().getInt("sips"));
        });
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SCPParticles.BLOOD.get(), GenericParticle.Provider::new);
    }

}
