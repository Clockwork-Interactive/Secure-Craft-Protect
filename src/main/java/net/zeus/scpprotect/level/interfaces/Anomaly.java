package net.zeus.scpprotect.level.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import org.apache.commons.lang3.tuple.Triple;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.HashMap;
import java.util.Map;
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

    /**
     * Gets called every frame
     */
    default void updateEffects(AnimationState<?> state) {
        if (getAnimations() != null && !getAnimations().isEmpty()) {
            Triple<String, RawAnimation, Predicate<AnimationState<?>>> animation = getAnimations().entrySet().stream()
                    .filter((entry) -> entry.getValue().getRight().test(state))
                    .min(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .orElse(getAnimations().get(-1));
            if (animation != null) {
                if (animation.getLeft().equals("none")) {
                    state.getController().stop();
                    return;
                }
                if (!this.isCurrentAnimation(state, animation.getMiddle()))
                    ((GeoEntity) this).triggerAnim("controller", animation.getLeft());
            }
        }
    }

    default boolean isCurrentAnimation(AnimationState<?> state, RawAnimation animation) {
        return state.getController() != null && state.isCurrentAnimation(animation) && !state.getController().hasAnimationFinished();
    }

    default void addContinuous(int priority, String id, RawAnimation animation, Predicate<AnimationState<?>> predicate) {
        if (priority < 0) {
            throw new IllegalArgumentException("Priority must be greater than 0");
        }
        if (getAnimations().containsKey(priority)) SCP.LOGGER.warn("Overwriting animation with priority %d".formatted(priority));
        getAnimations().put(priority, Triple.of(id, animation, predicate));
    }

    default void addDefault(String id, RawAnimation animation) {
        getAnimations().put(-1, Triple.of(id, animation, (state) -> false));
    }

    default HashMap<Integer, Triple<String, RawAnimation, Predicate<AnimationState<?>>>> getAnimations() {
        return null;
    }

    default boolean canContain() {
        return true;
    }

}
