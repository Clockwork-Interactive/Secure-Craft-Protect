package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public abstract class DefaultGeoBirdModel<T extends LivingEntity & GeoAnimatable> extends GeoModel<T> {

	@Override
	public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		this.onAnimate(animationState, animatable);

		if (animatable instanceof Anomaly anomaly && anomaly.doHeadAnimation(animationState)) {
			if (head != null) {
				EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

				head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
				head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
				head.setRotZ(0);
			}

		}


	}


	public void onAnimate(AnimationState<?> state, T animatable) {

	}

}
