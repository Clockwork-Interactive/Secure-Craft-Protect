package net.zeus.scpprotect.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.entity.custom.*;
import net.zeus.scpprotect.level.entity.custom.base.SCP173;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntity.SCP_096.get(), SCP096.createAttributes().build());
        event.put(ModEntity.SCP_173i.get(), SCP173.createAttributes().build());
        event.put(ModEntity.SCP_939.get(), SCP939.createAttributes().build());
        event.put(ModEntity.SCP_966.get(), SCP966.createAttributes().build());
        event.put(ModEntity.SCP_019_2.get(), SCP019_2.createAttributes().build());
        event.put(ModEntity.SCP_811.get(), SCP811.createAttributes().build());
    }

}
