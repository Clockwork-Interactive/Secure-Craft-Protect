package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.apache.commons.lang3.tuple.Triple;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.function.Predicate;

public abstract class SCPEntity extends Monster implements Anomaly, GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final HashMap<Integer, Triple<String, RawAnimation, Predicate<AnimationState<?>>>> animations = new HashMap<>();

    protected SCPEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public HashMap<Integer, Triple<String, RawAnimation, Predicate<AnimationState<?>>>> getAnimations() {
        return animations;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

}
