package net.zeus.scpprotect.event;

import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.refractionapi.refraction.sound.SoundUtil;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.capabilities.Capabilities;
import net.zeus.scpprotect.data.PlayerData;
import net.zeus.scpprotect.datagen.advancements.SCPCriteriaTriggers;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.effect.effects.PacificationEffect;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.items.SCP999BucketItem;
import net.zeus.scpprotect.level.item.items.SolidBucketMobItem;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.level.worldgen.dimension.SCPDimensions;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;

import java.util.*;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    public static Map<Player, PlayerData> PlayerRuntimeData = new HashMap<>();
    public static Map<Player, Integer> SCP_966_INSOMNIA = new HashMap<>();
    public static int SCP_966Max = 2000;
    public static BlockPos SCP106Escape; // Will change per runtime

    @SubscribeEvent
    public static void onLevelChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(SCPDimensions.SCP_106_LEVEL) && !event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            ServerLevel level = Objects.requireNonNull(player.level().getServer()).getLevel(SCPDimensions.SCP_106_LEVEL);
            if (level == null) throw new IllegalStateException("SCP-106 dimension is null");
            BlockPos spawn = new BlockPos(0, 0, 0);
            if (!level.getBlockState(spawn).is(SCPBlocks.DECAY_BLOCK.get())) {
                Optional<StructureTemplate> optional;
                try {
                    optional = level.getStructureManager().get(new ResourceLocation(SCP.MOD_ID, "scp_106"));
                } catch (ResourceLocationException resourcelocationexception) {
                    SCP.LOGGER.error("Failed to load structure template {}", resourcelocationexception.getMessage());
                    return;
                }
                optional.ifPresent(structureTemplate -> structureTemplate.placeInWorld(level, new BlockPos(-12, 0, -12), spawn, new StructurePlaceSettings(), player.getRandom(), 2));
            }
            player.teleportTo(-0.5F, 2, -0.5F);
            SoundUtil.playTrackingSound(player, SCPSounds.POCKET_DIMENSION_AMBIENCE.get(), true, -1);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void toolTipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof Anomaly anomaly) {
            SCP.SCPTypes scpTypes = anomaly.getClassType();
            SCP.SCPNames scpNames = anomaly.getSCPName();
            if (scpTypes != SCP.SCPTypes.UNCLASSIFIED) {
                event.getToolTip().add(scpTypes.component);
            }
            if (scpNames != SCP.SCPNames.UNDEFINED) {
                event.getToolTip().add(scpNames.component);
            }
            return;
        }

        if (event.getItemStack().getItem() instanceof BlockItem item && item.getBlock() instanceof Anomaly anomaly) {
            SCP.SCPTypes scpTypes = anomaly.getClassType();
            SCP.SCPNames scpNames = anomaly.getSCPName();
            if (scpTypes != SCP.SCPTypes.UNCLASSIFIED) {
                event.getToolTip().add(scpTypes.component);
            }
            if (scpNames != SCP.SCPNames.UNDEFINED) {
                event.getToolTip().add(scpNames.component);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SuppressWarnings("unchecked")
    public static void livingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            SCP_966_INSOMNIA.put(player, 0);
            if (player instanceof ServerPlayer serverPlayer) {
                ModMessages.sendToPlayer(new VignetteS2CPacket(0, true, false), serverPlayer);
            }
        }
        if (event.getEntity() instanceof Anomaly) {
            event.getEntity().level().getCapability(Capabilities.SCP_SAVED_DATA).ifPresent((data) ->
                    data.removeSCP((EntityType<? extends Anomaly>) event.getEntity().getType())
            );
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) return;
        Player player = event.player;
        ItemStack itemstackMain = player.getMainHandItem();
        ItemStack itemstackOff = player.getOffhandItem();

        if (itemstackMain.is(SCPItems.ODD_CLIMBERS.get()) || itemstackOff.is(SCPItems.ODD_CLIMBERS.get())) {
            if (player.horizontalCollision) {
                Vec3 initialVec = player.getDeltaMovement();
                Vec3 climbVec = new Vec3(initialVec.x, 0.2D, initialVec.z);
                player.setDeltaMovement(climbVec.x * 0.91D,
                        climbVec.y * 0.98D, climbVec.z * 0.91D);
            }
        }

        if (!player.level().isClientSide) {

            player.level().getEntities(player, player.getBoundingBox().inflate(12.0F), entity -> entity instanceof Anomaly).forEach(entity -> {
                if (Vec3Helper.isInAngle(player, entity.getEyePosition(), 90) && player.hasLineOfSight(entity)) {
                    SCPCriteriaTriggers.SEEN.trigger((ServerPlayer) player, entity);
                }
            });

            ServerLevel scp106Dim = Objects.requireNonNull(player.level().getServer()).getLevel(SCPDimensions.SCP_106_LEVEL);
            if (scp106Dim != null && player.level().dimension().equals(SCPDimensions.SCP_106_LEVEL)) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false));
                if (player.distanceToSqr(0, 0, 0) >= 49) {
                    SCP106Escape = SCP106Escape == null ? player.getRandom().nextInt(8) == 0 ? player.blockPosition() : null : SCP106Escape;
                    boolean live = SCP106Escape != null && Math.sqrt(SCP106Escape.distSqr(player.blockPosition())) <= 1.5F;
                    if (!live) {
                        player.kill();
                    } else {
                        player.getCapability(Capabilities.SCP_DATA).ifPresent(scpData -> {
                            ServerLevel homeDim = Objects.requireNonNull(player.level().getServer()).getLevel(ResourceKey.create(Registries.DIMENSION, scpData.scp106TakenDim));
                            if (homeDim == null) {
                                player.kill();
                                return;
                            }
                            SoundUtil.playLocalSound(player, SCPSounds.POCKET_DIMENSION_EXIT.get());
                            SCPAdvancements.grant(player, SCPAdvancements.NO_MANS_LAND);
                            player.fallDistance = 0.0F;
                            player.teleportTo(homeDim, scpData.scp106TakenPos.getX(), scpData.scp106TakenPos.getY(), scpData.scp106TakenPos.getZ(), Set.of(), player.getYRot(), player.getXRot());
                        });
                    }
                }
            }
        }

        if (!player.hasEffect(SCPEffects.INSOMNIA.get())) return;
        else SCP_966_INSOMNIA.putIfAbsent(player, 0);
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

    @SubscribeEvent
    public static void onBlockEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() == null) return;
        ServerLevel scp106Dim = Objects.requireNonNull(event.getEntity().level().getServer()).getLevel(SCPDimensions.SCP_106_LEVEL);
        if (event.isCancelable() && scp106Dim != null && event.getEntity().level().dimension().equals(SCPDimensions.SCP_106_LEVEL)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void sleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity().hasEffect(SCPEffects.INSOMNIA.get())) {
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttack(LivingAttackEvent event) {
        if (!event.isCancelable()) return;
        if (event.getSource().getEntity() instanceof LivingEntity living && living.hasEffect(SCPEffects.AMPUTATED.get())) {
            event.setCanceled(true);
        }
        if (event.getEntity().hasEffect(SCPEffects.PACIFICATION.get())) {
            event.getEntity().removeEffect(SCPEffects.PACIFICATION.get());
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
        MobEffect effect = event.getEffect();

        if (effect == SCPEffects.AMPUTATED.get()) {
            if (entity instanceof Player) {
                Minecraft.getInstance().gameRenderer.setRenderHand(true);
            }
        } else if (effect == SCPEffects.PACIFICATION.get()) {
            PacificationEffect.onRemove(entity);
        }
    }

    @SubscribeEvent
    public static void effectExpires(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();

        if (effect == SCPEffects.PACIFICATION.get()) {
            PacificationEffect.onRemove(entity);
        }
    }

}
