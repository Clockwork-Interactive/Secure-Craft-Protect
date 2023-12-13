package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DefaultGeoTetrapodModel<T extends LivingEntity & GeoAnimatable> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return null;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return null;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }

	@Override
	public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		CoreGeoBone bottomLeftLeg = getAnimationProcessor().getBone("leg3");
		CoreGeoBone bottomRightLeg = getAnimationProcessor().getBone("leg4");
		CoreGeoBone topRightLeg = getAnimationProcessor().getBone("leg2");
		CoreGeoBone topLeftLeg = getAnimationProcessor().getBone("leg1");
		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

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
