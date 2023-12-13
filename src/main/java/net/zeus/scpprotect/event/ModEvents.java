package net.zeus.scpprotect.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.capabilities.SCPData;
import net.zeus.scpprotect.capabilities.SCPDataProvider;
import net.zeus.scpprotect.level.effect.ModEffects;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(SCPDataProvider.SCP_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(SCP.MOD_ID, "propertiesscpdata"), new SCPDataProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {

            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(SCPDataProvider.SCP_DATA).ifPresent(oldStore -> {
                event.getEntity().getCapability(SCPDataProvider.SCP_DATA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();

        }
    }

    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().hasEffect(ModEffects.AMPUTATED.get())) {
            event.getRenderer().getModel().rightArm.visible = false;
            event.getRenderer().getModel().leftArm.visible = false;
            event.getRenderer().getModel().rightSleeve.visible = false;
            event.getRenderer().getModel().leftSleeve.visible = false;
        }
    }

}
