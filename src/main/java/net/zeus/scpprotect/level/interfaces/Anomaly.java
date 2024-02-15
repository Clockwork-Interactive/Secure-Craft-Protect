package net.zeus.scpprotect.level.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.zeus.scpprotect.SCP;

public interface Anomaly {
    
    SCP.SCPTypes getClassType();


    default void onKillEntity(LivingEntity entity) {

    }

}
