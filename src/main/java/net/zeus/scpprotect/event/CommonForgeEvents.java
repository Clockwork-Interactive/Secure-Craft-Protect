package net.zeus.scpprotect.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.data.PlayerData;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.items.SCP999BucketItem;
import net.zeus.scpprotect.level.item.items.SolidBucketMobItem;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    public static Map<Player, PlayerData> PlayerRuntimeData = new HashMap<>();
    public static Map<Player, Integer> SCP_966_INSOMNIA = new HashMap<>();
    public static int SCP_966Max = 2000;

    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerData.init(event.getEntity());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void toolTipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof Anomaly anomaly) {
            SCP.SCPTypes scpTypes = anomaly.getClassType();
            if (scpTypes == SCP.SCPTypes.EUCLID) {
                event.getToolTip().add(Component.literal("Euclid").withStyle(ChatFormatting.YELLOW));
            } else if (scpTypes == SCP.SCPTypes.KETER) {
                event.getToolTip().add(Component.literal("Keter").withStyle(ChatFormatting.RED));
            } else {
                event.getToolTip().add(Component.literal("Safe").withStyle(ChatFormatting.GREEN));
            }
            return;
        }
        if (event.getItemStack().getItem() instanceof BlockItem item && item.getBlock() instanceof Anomaly anomaly) {
            SCP.SCPTypes scpTypes = anomaly.getClassType();
            if (scpTypes == SCP.SCPTypes.EUCLID) {
                event.getToolTip().add(Component.literal("Euclid").withStyle(ChatFormatting.YELLOW));
            } else if (scpTypes == SCP.SCPTypes.KETER) {
                event.getToolTip().add(Component.literal("Keter").withStyle(ChatFormatting.RED));
            } else {
                event.getToolTip().add(Component.literal("Safe").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            SCP_966_INSOMNIA.put(player, 0);
            if (player instanceof ServerPlayer serverPlayer) {
                ModMessages.sendToPlayer(new VignetteS2CPacket(0, true, false), serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) return;
        Player player = event.player;
        if (!player.hasEffect(SCPEffects.INSOMNIA.get())) return; else SCP_966_INSOMNIA.putIfAbsent(player, 0);
        SCP_966_INSOMNIA.putIfAbsent(player, 0);
        SCP_966_INSOMNIA.put(player, SCP_966_INSOMNIA.get(player) + 1);
        if (SCP_966_INSOMNIA.get(player) > 2000 && !player.level().isClientSide && player.level() instanceof ServerLevel serverLevel) {
            if (SCP_966_INSOMNIA.get(player) % 600 == 0 && player.level().canSeeSky(player.getOnPos()) && player.level().dimensionType().hasSkyLight() && !player.level().isDay()) {
                BlockPos blockpos = player.getOnPos();
                BlockPos blockpos1 = blockpos.above(20 + player.getRandom().nextInt(15)).east(-10 + player.getRandom().nextInt(21)).south(-10 + player.getRandom().nextInt(21));
                BlockState blockstate = player.level().getBlockState(blockpos1);
                FluidState fluidstate = player.level().getFluidState(blockpos1);
                if (NaturalSpawner.isValidEmptySpawnBlock(player.level(), blockpos1, blockstate, fluidstate, EntityType.PHANTOM)) {
                    SpawnGroupData spawngroupdata = null;
                    for (int i1 = 0; i1 < 10; ++i1) {
                        Phantom phantom = EntityType.PHANTOM.create(player.level());
                        if (phantom != null) {
                            phantom.moveTo(blockpos1, 0.0F, 0.0F);
                            spawngroupdata = phantom.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.NATURAL, spawngroupdata, null);
                            serverLevel.addFreshEntityWithPassengers(phantom);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void sleepEvent(PlayerSleepInBedEvent event) {
        if (SCP_966_INSOMNIA.get(event.getEntity()) > SCP_966Max) {
            event.setResult(Player.BedSleepingProblem.NOT_SAFE);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void interactEvent(PlayerInteractEvent event) {
        if (!event.isCancelable()) return;
        if (event.getEntity().hasEffect(SCPEffects.AMPUTATED.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Anomaly anomaly) {
            anomaly.onKillEntity(event.getEntity());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();

        // SCP-999

        if (event.getTarget() instanceof Bucketable bucketable &&
                bucketable.getBucketItemStack().getItem() instanceof SCP999BucketItem bucketItem
                && !(stack.getItem() instanceof SCP999BucketItem) && event.getTarget().isAlive()) {

            if (stack.getItem() instanceof BucketItem item) {
                ItemStack bucketItemStack = bucketable.getBucketItemStack();
                Fluid requiredFluid = bucketItem.getFluid();
                Fluid fluid = item.getFluid();
                Entity player = event.getEntity();
                if (fluid.equals(requiredFluid)) {
                    player.playSound(SoundEvents.BUCKET_FILL_FISH, 0.7F, 1.0F);
                    bucketable.saveToBucketTag(bucketItemStack);
                    event.getEntity().setItemInHand(event.getHand(), bucketItemStack);
                    event.getTarget().discard();
                }
            }

        }

        // SCP-3199 Egg

        if (event.getTarget() instanceof Bucketable bucketable &&
                bucketable.getBucketItemStack().getItem() instanceof SolidBucketMobItem bucketItem
                && !(stack.getItem() instanceof SolidBucketMobItem) && event.getTarget().isAlive()) {

            if (stack.getItem() instanceof SolidBucketItem item) {
                ItemStack bucketItemStack = bucketable.getBucketItemStack();
                Block requiredBlock = bucketItem.getBlock();
                Block block = item.getBlock();
                if (block.equals(requiredBlock)) {
                    bucketable.saveToBucketTag(bucketItemStack);
                    event.getEntity().setItemInHand(event.getHand(), bucketItemStack);
                    event.getTarget().discard();
                }
            }

        }
    }

    @SubscribeEvent
    public static void effectRemoved(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player && event.getEffectInstance().getEffect().equals(SCPEffects.AMPUTATED.get())) {
            Minecraft.getInstance().gameRenderer.setRenderHand(true);
        }
    }
}
