package net.zeus.scppancakes.event;

import net.minecraft.world.entity.Pose;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.ModEntity;
import net.zeus.scppancakes.entity.custom.SCP096;
import net.zeus.scppancakes.entity.custom.SCP173i;
import net.zeus.scppancakes.entity.custom.SCP939;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SCPPancakes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    public static Map<SCP096, Pose> currentPose = new HashMap<>();


    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntity.SCP_096.get(), SCP096.createAttributes().build());
        event.put(ModEntity.SCP_173i.get(), SCP173i.createAttributes().build());
        //event.put(ModEntity.SCP_939.get(), SCP939.createAttributes().build());
    }

}
