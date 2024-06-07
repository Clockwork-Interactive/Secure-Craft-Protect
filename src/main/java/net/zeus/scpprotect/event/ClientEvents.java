package net.zeus.scpprotect.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.refractionapi.refraction.quest.client.ClientQuestInfo;
import net.refractionapi.refraction.quest.points.LocationPoint;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.entities.SCP966;
import net.zeus.scpprotect.level.item.SCPItems;
import software.bernie.geckolib.event.GeoRenderEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static boolean markedNods = false;
    private static boolean markedAmnesia = false;
    public static int clientTick = 0;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            // Holy shit, this is a mess. TODO refactor this. -- Zeus
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(SCPItems.NODS.get()) && !player.hasEffect(SCPEffects.AMNESIA.get())) {
                if (!markedNods) {
                    markedNods = true;
                    Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation(SCP.MOD_ID, "shader/nods.json"));
                }
            } else if (markedNods) {
                markedNods = false;
                Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
            }

            if (player.hasEffect(SCPEffects.AMNESIA.get())) {
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

            if (player.hasEffect(SCPEffects.CORROSION.get())) {
                Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
                Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation(SCP.MOD_ID, "shader/motion_blur.json"));
            }

        }
        if (event.phase.equals(TickEvent.Phase.END)) return;
        clientTick++;
        if (PlayerClientData.vignetteTick > 0) {
            PlayerClientData.vignetteTick--;
        }
        if (player != null && player.hasEffect(SCPEffects.ANXIOUS.get())) {
            if (PlayerClientData.currentFov < Minecraft.getInstance().options.fov().get() / 1.5F || PlayerClientData.pulse) {
                PlayerClientData.fovTick--;
                PlayerClientData.pulse = PlayerClientData.fovTick > 0;
                return;
            }
            PlayerClientData.fovTick++;
        } else {
            PlayerClientData.fovTick = 0;
            PlayerClientData.currentFov = Integer.MAX_VALUE;
        }
    }

    @SubscribeEvent
    public static void renderArrow(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            Player player = Minecraft.getInstance().player;
            if (player == null || Minecraft.getInstance().gameRenderer.getMainCamera().isDetached()) return;
            CompoundTag questInfo = ClientQuestInfo.tag;
            if (questInfo == null || questInfo.isEmpty()) return;
            ListTag points = questInfo.getList("points", Tag.TAG_COMPOUND);
            for (Tag point : points) {
                CompoundTag pointTag = (CompoundTag) point;
                String genericID = pointTag.getString("genericID");
                if (pointTag.contains("scpClass") && genericID.equals(LocationPoint.class.getSimpleName())) {
                    double x = pointTag.getDouble("x");
                    double y = pointTag.getDouble("y");
                    double z = pointTag.getDouble("z");
                    PlayerClientData.color = new Color(SCP.SCPTypes.values()[pointTag.getInt("scpClass")].component.getStyle().getColor().getValue());
                    Vec3 targetPos = new Vec3(x, y, z);
                    Vec3 playerPos = player.position();
                    double angle = Math.atan2(targetPos.z - playerPos.z, targetPos.x - playerPos.x);
                    double radius = 0.3D;
                    Vec3 renderPos = new Vec3(radius * Math.cos(angle), -0.2D, radius * Math.sin(angle));
                    PoseStack stack = event.getPoseStack();

                    stack.pushPose();
                    stack.translate(renderPos.x, renderPos.y, renderPos.z);
                    stack.rotateAround(Axis.XP.rotationDegrees(90), 0, 0, 0);
                    stack.rotateAround(Axis.ZP.rotationDegrees((float) (angle * 180 / Math.PI + 270)), 0, 0, 0);
                    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                    MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
                    itemRenderer.renderStatic(SCPItems.ARROW.get().getDefaultInstance(), ItemDisplayContext.GROUND, 140, 0, event.getPoseStack(), source, player.level(), 0);
                    stack.popPose();
                }
            }
        }
    }

    @SubscribeEvent
    public static void geoRender(GeoRenderEvent.Entity.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (event.getEntity() instanceof SCP966 scp966 && player != null) {
            if (player.hasEffect(MobEffects.NIGHT_VISION) || player.getItemBySlot(EquipmentSlot.HEAD).is(SCPItems.NODS.get()) || (scp966.isOnFire() && scp966.hurtTime > 0)) {
                return;
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void fovEvent(ViewportEvent.ComputeFov event) {
        if (PlayerClientData.fovTick > 0) {
            event.setFOV(event.getFOV() - (double) Mth.lerp(PlayerClientData.fovTick / (Minecraft.getInstance().options.fov().get() / 1.1F), 0, 1.0F) * PlayerClientData.fovTick / 6);
            PlayerClientData.currentFov = (int) event.getFOV();
        }
    }

}
