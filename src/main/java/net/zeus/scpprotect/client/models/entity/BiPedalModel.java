package net.zeus.scpprotect.client.models.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

public abstract class BiPedalModel<T extends LivingEntity & GeoAnimatable> extends DefaultGeoBiPedalModel<T> {

    public abstract String model(int process, T animatable);
    public abstract boolean hasAnimation();

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "geo/entity/" + this.model(1, animatable) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(SCP.MOD_ID, "textures/entity/" + this.model(2, animatable) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (!this.hasAnimation()) return null;
        return new ResourceLocation(SCP.MOD_ID, "animations/entity/animations_" + this.model(3, animatable) + ".animation.json");
    }

}
