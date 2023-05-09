package net.zeus.scppancakes.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.client.models.SCP173Model;
import net.zeus.scppancakes.entity.custom.SCP173i;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP173EntityRenderer extends GeoEntityRenderer<SCP173i> {

    public SCP173EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP173Model());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(SCP173i animatable) {
            return new ResourceLocation(SCPPancakes.MOD_ID, "textures/entity/scp_173i_default_texture.png");
    }

    @Override
    public RenderType getRenderType(SCP173i animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
