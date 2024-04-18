package net.zeus.scpprotect.client.models.entity;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP058;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SCP058Model extends GeoModel<SCP058> {

	@Override
	public ResourceLocation getModelResource(SCP058 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"geo/entity/scp_058.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SCP058 animatable) {
		return new ResourceLocation(SCP.MOD_ID,"textures/entity/scp_058.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SCP058 scp058) {
		return null;
	}

	@Override
	public void setCustomAnimations(SCP058 animatable, long instanceId, AnimationState<SCP058> animationState) {
		CoreGeoBone bottomLeftLeg = getAnimationProcessor().getBone("leg3");
		CoreGeoBone bottomRightLeg = getAnimationProcessor().getBone("leg4");
		CoreGeoBone topRightLeg = getAnimationProcessor().getBone("leg2");
		CoreGeoBone topLeftLeg = getAnimationProcessor().getBone("leg1");
		float partialTick = Minecraft.getInstance().getPartialTick();
		float speed = 0.0F;
		float position = 0.0F;

		if (animatable.isAlive()) {
			speed = animatable.walkAnimation.speed(partialTick);
			position = animatable.walkAnimation.position(partialTick);
			if (speed > 1.0F) speed = 1.0F;
		}

		if (bottomRightLeg != null && bottomLeftLeg != null) {
			bottomRightLeg.updateRotation(Mth.cos(position * 0.6662F + (float) Math.PI) * 1.4F * speed * 0.5F, 0.0F, 0.0F);
			bottomLeftLeg.updateRotation(Mth.cos(position * 0.6662F) * 1.4F * speed * 0.5F, 0.0F, 0.0F);
		}
		if (topRightLeg != null && topLeftLeg != null) {
			topRightLeg.updateRotation(Mth.cos(position * 0.6662F) * 1.4F * speed * 0.5F, 0.0F, 0.0F);
			topLeftLeg.updateRotation(Mth.cos(position * 0.6662F + (float) Math.PI) * 1.4F * speed * 0.5F, 0.0F, 0.0F);
		}
	}
}