package net.zeus.scppancakes.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.client.models.SCP939Model;
import net.zeus.scppancakes.entity.custom.SCP939;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP939EntityRenderer extends GeoEntityRenderer<SCP939> {

    public SCP939EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP939Model());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(SCP939 animatable) {
        return new ResourceLocation(SCPPancakes.MOD_ID, "textures/entity/scp_096_calm_texture.png");
    }

    @Override
    public RenderType getRenderType(SCP939 animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
