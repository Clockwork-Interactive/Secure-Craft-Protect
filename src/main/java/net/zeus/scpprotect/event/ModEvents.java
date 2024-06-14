package net.zeus.scpprotect.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.refractionapi.refraction.capabilities.CapabilityUtil;
import net.refractionapi.refraction.capabilities.Data;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.capabilities.Capabilities;
import net.zeus.scpprotect.capabilities.SCPDataProvider;
import net.zeus.scpprotect.capabilities.SCPSavedDataProvider;
import net.zeus.scpprotect.level.effect.SCPEffects;

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

    @SubscribeEvent
    public static void render(RenderPlayerEvent.Pre event) {
        if (event.getEntity().hasEffect(SCPEffects.AMPUTATED.get())) {
            event.getRenderer().getModel().rightArm.visible = false;
            event.getRenderer().getModel().leftArm.visible = false;
            event.getRenderer().getModel().rightSleeve.visible = false;
            event.getRenderer().getModel().leftSleeve.visible = false;
        }
    }

}
