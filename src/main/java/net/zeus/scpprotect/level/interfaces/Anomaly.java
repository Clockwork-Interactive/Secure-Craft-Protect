package net.zeus.scpprotect.level.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.HashMap;
import java.util.function.Predicate;

public interface Anomaly {

    SCP.SCPTypes getClassType();

    default void onKillEntity(LivingEntity entity) {

    }

    default boolean doArmAnimations(AnimationState<?> state) {
        return true;
    }

    default boolean doLegAnimations(AnimationState<?> state) {
        return true;
    }

    default boolean doHeadAnimation(AnimationState<?> state) {
        return true;
    }

    default boolean doAnyAnimations(AnimationState<?> state) {
        return true;
    }

    // TODO
    default void addContinous(int priority, String id, RawAnimation animation, Predicate<AnimationState<?>> predicate) {

    }

    // TODO
    default void addDefault(RawAnimation animation) {

    }

}
