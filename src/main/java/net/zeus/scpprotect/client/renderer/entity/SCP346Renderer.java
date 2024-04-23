package net.zeus.scpprotect.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP131Model;
import net.zeus.scpprotect.client.models.entity.SCP346Model;
import net.zeus.scpprotect.level.entity.entities.SCP131;
import net.zeus.scpprotect.level.entity.entities.SCP346;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP346Renderer extends GeoEntityRenderer<SCP346> {
    public SCP346Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP346Model());
        this.shadowRadius = 0.4F;
    }

    public void render(SCP346 pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            this.shadowRadius = 0.25F;
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, pBuffer, pPackedLight);
    }
}
