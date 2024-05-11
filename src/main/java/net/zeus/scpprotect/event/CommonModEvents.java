package net.zeus.scpprotect.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.entity.entities.*;
import net.zeus.scpprotect.level.entity.entities.SCP173;
import net.zeus.scpprotect.level.entity.misc.ContainmentBox;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(SCPEntities.SCP_058.get(), SCP058.createAttributes().build());
        event.put(SCPEntities.SCP_096.get(), SCP096.createAttributes().build());
        event.put(SCPEntities.SCP_173.get(), SCP173.createAttributes().build());
        event.put(SCPEntities.SCP_939.get(), SCP939.createAttributes().build());
        event.put(SCPEntities.SCP_966.get(), SCP966.createAttributes().build());
        event.put(SCPEntities.SCP_019_2.get(), SCP019_2.createAttributes().build());
        event.put(SCPEntities.SCP_811.get(), SCP811.createAttributes().build());
        event.put(SCPEntities.SCP_3199.get(), SCP3199.createAttributes().build());
        event.put(SCPEntities.SCP_3199_EGG.get(), SCP3199Egg.createAttributes().build());
        event.put(SCPEntities.CONTAINMENT_BOX.get(), ContainmentBox.createAttributes().build());
        event.put(SCPEntities.REBEL.get(), Rebel.createAttributes().build());
        event.put(SCPEntities.SCP_111.get(), SCP111.createAttributes().build());
        event.put(SCPEntities.SCP_131.get(), SCP131.createAttributes().build());
        event.put(SCPEntities.SCP_346.get(), SCP346.createAttributes().build());
        event.put(SCPEntities.SCP_999.get(), SCP999.createAttributes().build());
        event.put(SCPEntities.SCP_049.get(), SCP049.createAttributes().build());
        event.put(SCPEntities.SCP_049_2.get(), SCP049_2.createAttributes().build());
    }

}
