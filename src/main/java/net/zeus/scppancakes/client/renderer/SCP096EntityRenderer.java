package net.zeus.scppancakes.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.client.models.SCP096Model;
import net.zeus.scppancakes.entity.custom.SCP096Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP096EntityRenderer extends GeoEntityRenderer<SCP096Entity> {

    public SCP096EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP096Model());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(SCP096Entity animatable) {
        if (this.animatable.isTriggered()) {
            return new ResourceLocation(SCPPancakes.MOD_ID, "textures/entity/scp_096_rage_texture.png");
        } else {
            return new ResourceLocation(SCPPancakes.MOD_ID, "textures/entity/scp_096_calm_texture.png");
        }
    }

    @Override
    public RenderType getRenderType(SCP096Entity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
