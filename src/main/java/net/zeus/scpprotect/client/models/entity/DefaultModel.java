package net.zeus.scpprotect.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.tickable.PlayableTickableSound;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> {

    private final HashMap<T, AtomicReference<PlayableTickableSound>> idleSounds = new HashMap<>();

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

        if (this.hasIdle()) {
            PlayableTickableSound idle = getIdleSound(animatable);
            if (idle != null && canIdlePlay(animatable) && !idle.isPlaying) {
                idle.isPlaying = true;
                Minecraft.getInstance().getSoundManager().play(idle);
            }
            if (idle != null && (!canIdlePlay(animatable) || !idle.isPlaying || (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isDeadOrDying()))) {
                resetIdle(animatable);
            }
        }

    }

    public <E extends GeoEntity> void rotateGeoHead(String headBone, AnimationState<E> state) {
        CoreGeoBone head = this.getAnimationProcessor().getBone(headBone);
        if (head != null) {
            EntityModelData modelData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(modelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(modelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    public boolean hasIdle() {
        return false;
    }

    public PlayableTickableSound createIdle(T animatable) {
        return null;
    }

    /**
     * Do not override this method, use {@link #createIdle(T animatable)} instead
     */
    public PlayableTickableSound getIdleSound(T animatable) {
        getIdle(animatable).set(getIdle(animatable).get() == null ? createIdle(animatable) : getIdle(animatable).get());
        return getIdle(animatable).get();
    }

    public void resetIdle(T animatable) {
        if (getIdle(animatable).get() != null)
            Minecraft.getInstance().getSoundManager().stop(getIdle(animatable).get());
        getIdle(animatable).set(null);
    }

    public AtomicReference<PlayableTickableSound> getIdle(T animatable) {
        return idleSounds.computeIfAbsent(animatable, (key) -> new AtomicReference<>(null));
    }

    public boolean canIdlePlay(T animatable) {
        return true;
    }

}
