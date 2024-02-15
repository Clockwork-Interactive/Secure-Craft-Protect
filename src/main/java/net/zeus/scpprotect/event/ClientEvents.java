package net.zeus.scpprotect.event;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.data.PlayerData;
import net.zeus.scpprotect.level.effect.ModEffects;
import net.zeus.scpprotect.level.entity.entities.SCP966;
import net.zeus.scpprotect.level.item.ModItems;
import software.bernie.geckolib.event.GeoRenderEvent;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static boolean markedNods = false;
    private static boolean markedAmnesia = false;
    public static int clientTick = 0;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {

            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NODS.get()) && !player.hasEffect(ModEffects.AMNESIA.get())) {
                if (!markedNods) {
                    markedNods = true;
                    Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation(SCP.MOD_ID, "shader/nods.json"));
                }
            } else if (markedNods) {
                markedNods = false;
                Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
            }

            if (player.hasEffect(ModEffects.AMNESIA.get())) {
                if (!markedAmnesia) {
                    Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
                    markedAmnesia = true;
                    Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation(SCP.MOD_ID, "shader/blur.json"));
                }
            } else if (markedAmnesia) {
                markedAmnesia = false;
                markedNods = false;
                Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
            }

        }
        if (event.phase.equals(TickEvent.Phase.END)) return;
        clientTick++;
        if (PlayerClientData.vignetteTick > 0) {
            PlayerClientData.vignetteTick--;
        }
        PlayerData data = PlayerData.getData(player);
        if (player != null && player.hasEffect(ModEffects.ANXIOUS.get())) {
            if (data.currentFov < Minecraft.getInstance().options.fov().get() / 1.5F || data.pulse) {
                data.fovTick--;
                data.pulse = data.fovTick > 0;
                return;
            }
            data.fovTick++;
        } else {
            data.fovTick = 0;
            data.currentFov = Integer.MAX_VALUE;
        }
    }

    @SubscribeEvent
    public static void geoRender(GeoRenderEvent.Entity.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (event.getEntity() instanceof SCP966 scp966 && player != null) {
            if (player.hasEffect(MobEffects.NIGHT_VISION) || player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NODS.get()) || (scp966.isOnFire() && scp966.hurtTime > 0)) {
                return;
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void fovEvent(ViewportEvent.ComputeFov event) {
        Player player = Minecraft.getInstance().player;
        PlayerData data = PlayerData.getData(player);
        if (data.fovTick > 0) {
            event.setFOV(event.getFOV() - (double) Mth.lerp(data.fovTick / (Minecraft.getInstance().options.fov().get() / 1.1F), 0, 1.0F) * data.fovTick / 6);
            data.currentFov = (int) event.getFOV();
        }
    }

}
