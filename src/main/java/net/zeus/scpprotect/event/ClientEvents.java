package net.zeus.scpprotect.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PostChain;
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
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.refractionapi.refraction.quest.client.ClientQuestInfo;
import net.refractionapi.refraction.quest.points.LocationPoint;
import net.refractionapi.refraction.runnable.PriorityTicker;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.client.data.PlayerClientData;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.entities.SCP966;
import net.zeus.scpprotect.level.item.SCPItems;
import software.bernie.geckolib.event.GeoRenderEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    public static int clientTick = 0;
    private static final ResourceLocation EMPTY = new ResourceLocation("empty");
    private static final ResourceLocation MOTION_BLUR = new ResourceLocation(SCP.MOD_ID, "shader/motion_blur.json");
    private static final ResourceLocation BLUR = new ResourceLocation(SCP.MOD_ID, "shader/blur.json");
    private static final ResourceLocation NODS = new ResourceLocation(SCP.MOD_ID, "shader/nods.json");
    private static ResourceLocation currentShader = EMPTY;
    private static final PriorityTicker<Player> tickable = new PriorityTicker<>() {{
        addTask(0, (player) -> player.hasEffect(SCPEffects.CORROSION.get()), (player) -> currentShader = MOTION_BLUR);
        addTask(1, (player) -> player.hasEffect(SCPEffects.AMNESIA.get()), (player) -> currentShader = BLUR);
        addTask(2, (player) -> player.getItemBySlot(EquipmentSlot.HEAD).is(SCPItems.NODS.get()), (player) -> currentShader = NODS);
        sideTask((player) -> {
            PostChain currentEffect = Minecraft.getInstance().gameRenderer.currentEffect();
            if ((currentEffect == null || !currentEffect.getName().equals(currentShader.toString())) && !currentShader.equals(EMPTY)) {
                Minecraft.getInstance().gameRenderer.loadEffect(currentShader);
            }
        });
        onNoTask((player) -> {
            Minecraft.getInstance().gameRenderer.checkEntityPostEffect(null);
            currentShader = EMPTY;
        });
    }};

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            tickable.tick(player);
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
    public static void render(RenderPlayerEvent.Pre event) {
        if (event.getEntity().hasEffect(SCPEffects.AMPUTATED.get())) {
            event.getRenderer().getModel().rightArm.visible = false;
            event.getRenderer().getModel().leftArm.visible = false;
            event.getRenderer().getModel().rightSleeve.visible = false;
            event.getRenderer().getModel().leftSleeve.visible = false;
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
