package net.zeus.scpprotect.data;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

public class DeobfuscatedUtil {

    public static final Method MobAmbientSound = ObfuscationReflectionHelper.findMethod(Mob.class, "m_7515_");
    //public static final Method ReadAdditional = ObfuscationReflectionHelper.findMethod(Entity.class, "m_7378_");

}
