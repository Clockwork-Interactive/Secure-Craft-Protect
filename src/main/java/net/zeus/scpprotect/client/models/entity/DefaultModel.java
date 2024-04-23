package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import net.zeus.scpprotect.level.entity.entities.SCP999;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public abstract class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> {

    public abstract String model(int process, T animatable);

    public abstract String type(T animatable);

    public abstract boolean hasAnimation(T animatable);

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/" + this.type(animatable) + "/" + this.model(1, animatable) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/" + this.type(animatable) + "/" + this.model(2, animatable) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (!this.hasAnimation(animatable)) return null;
        return new ResourceLocation(SCP.MOD_ID, "animations/" + this.type(animatable) + "/animations_" + this.model(3, animatable) + ".animation.json");
    }
}
