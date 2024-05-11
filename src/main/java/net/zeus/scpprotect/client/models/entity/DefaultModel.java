package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public abstract class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> {

    public abstract String model(int process, T animatable);

    public abstract String type(T animatable);

    public abstract boolean hasAnimation(T animatable);

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/%s/%s.geo.json".formatted(this.type(animatable), this.model(1, animatable)));
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/%s/%s.png".formatted(this.type(animatable), this.model(2, animatable)));
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (!this.hasAnimation(animatable)) return null;
        return new ResourceLocation(SCP.MOD_ID, "animations/%s/%s.animation.json".formatted(this.type(animatable), this.model(3, animatable)));
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        if (animatable instanceof Anomaly anomaly) anomaly.updateEffects(animationState);
    }

    public <E extends GeoEntity> void rotateGeoHead(String headBone, AnimationState<E> state) {
        CoreGeoBone head = this.getAnimationProcessor().getBone(headBone);
        if (head != null) {
            EntityModelData modelData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(modelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(modelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}
