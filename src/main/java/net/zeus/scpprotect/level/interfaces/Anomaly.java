package net.zeus.scpprotect.level.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;
import software.bernie.geckolib.core.animation.AnimationState;

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

}
