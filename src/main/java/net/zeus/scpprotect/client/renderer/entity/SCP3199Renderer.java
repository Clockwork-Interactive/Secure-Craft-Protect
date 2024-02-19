package net.zeus.scpprotect.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP3199Model;
import net.zeus.scpprotect.level.entity.entities.SCP3199;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP3199Renderer extends GeoEntityRenderer<SCP3199> {

    public SCP3199Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP3199Model());
    }


    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, SCP3199 animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.isBaby()) {
            widthScale = 0.5F;
            heightScale = 0.5F;
        }
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
