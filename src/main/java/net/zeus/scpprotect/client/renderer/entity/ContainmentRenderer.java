package net.zeus.scpprotect.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.zeus.scpprotect.client.models.entity.ContainmentModel;
import net.zeus.scpprotect.level.entity.entities.ContainmentBox;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ContainmentRenderer extends GeoEntityRenderer<ContainmentBox> {

    private final EntityRenderDispatcher dispatcher;

    public ContainmentRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ContainmentModel());
        this.dispatcher = renderManager.getEntityRenderDispatcher();
    }

    @Override
    public void render(ContainmentBox entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Entity heldEntity = entity.getHeldEntity();
        if (heldEntity != null) {
            poseStack.pushPose();
            poseStack.rotateAround(Axis.YN.rotationDegrees(entity.yBodyRot), 0, 0, 0);
            this.dispatcher.render(heldEntity,
                    0,
                    0.1F,
                    0,
                    0,
                    partialTick,
                    poseStack,
                    bufferSource,
                    packedLight
            );
            poseStack.popPose();
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
