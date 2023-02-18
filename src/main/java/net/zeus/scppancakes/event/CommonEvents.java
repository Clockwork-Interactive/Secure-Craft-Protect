package net.zeus.scppancakes.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.ModEntity;
import net.zeus.scppancakes.entity.custom.SCP096Entity;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SCPPancakes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    public static Map<SCP096Entity, Pose> currentPose = new HashMap<>(); // TODO Convert to packet if possible. Or use animation cache?

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntity.SCP_096.get(), SCP096Entity.createAttributes().build());
    }

}
