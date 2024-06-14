package net.zeus.scpprotect.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.refractionapi.refraction.capabilities.CapabilityUtil;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.capabilities.Capabilities;
import net.zeus.scpprotect.capabilities.SCPDataProvider;
import net.zeus.scpprotect.capabilities.SCPSavedDataProvider;

import static net.refractionapi.refraction.capabilities.CapabilityUtil.attachCapability;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            attachCapability(event, new SCPDataProvider(), Capabilities.SCP_DATA);
        }
    }

    @SubscribeEvent
    public static void attachLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
        attachCapability(event, new SCPSavedDataProvider(), Capabilities.SCP_SAVED_DATA);
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            CapabilityUtil.playerClone(event, Capabilities.SCP_DATA);
        }
    }

}
