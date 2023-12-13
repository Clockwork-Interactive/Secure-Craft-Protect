package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public abstract class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> {

    public abstract String model(int process);

    public abstract String type();

    public abstract boolean hasAnimation();

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/" + this.type() + "/" + this.model(1) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/" + this.type() + "/" + this.model(2) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (!this.hasAnimation()) return null;
        return new ResourceLocation(SCP.MOD_ID, "animations/" + this.type() + "animations_" + this.model(3) + ".animation.json");
    }
}
