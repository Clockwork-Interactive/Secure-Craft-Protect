package net.zeus.scppancakes.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.ModEntity;
import net.zeus.scppancakes.entity.custom.SCP096Entity;

@Mod.EventBusSubscriber(modid = SCPPancakes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntity.SCP_096.get(), SCP096Entity.createAttributes().build());
    }
}
