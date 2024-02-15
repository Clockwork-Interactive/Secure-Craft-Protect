package net.zeus.scpprotect.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.zeus.scpprotect.client.models.entity.SCP966Model;
import net.zeus.scpprotect.level.entity.entities.SCP966;
import net.zeus.scpprotect.level.item.ModItems;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP966Renderer extends GeoEntityRenderer<SCP966> {
    public SCP966Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP966Model());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SCP966 animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float a = 1.0F;
        if (Minecraft.getInstance().player != null && (Minecraft.getInstance().player.hasEffect(MobEffects.NIGHT_VISION) || animatable.isOnFire() && animatable.hurtTime > 0) && !Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NODS.get())) {
            a = 0.2F;
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, a);
    }
}
