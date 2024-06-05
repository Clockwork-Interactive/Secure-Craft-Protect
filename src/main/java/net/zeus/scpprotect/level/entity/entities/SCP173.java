package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.refractionapi.refraction.camerashake.CameraShakeHandler;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.refractionapi.refraction.runnable.RunnableCooldownHandler;
import net.refractionapi.refraction.runnable.RunnableHandler;
import net.refractionapi.refraction.sound.SoundUtil;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.blocks.SculptureExcrement;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.BlinkS2CPacket;
import net.zeus.scpprotect.networking.S2C.PlayLocalSoundS2C;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCP173 extends SCPEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int blinkTick = 0;
    public final HashMap<Player, Integer> teleports = new HashMap<>();
    public boolean inEvent = false;
    public static EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(SCP173.class, EntityDataSerializers.INT);

    public SCP173(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.setPersistenceRequired();
        this.setPassable(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 99.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 700.0F)
                .add(Attributes.FOLLOW_RANGE, 1000.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("type", this.entityData.get(TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("type"))
            this.entityData.set(TYPE, pCompound.getInt("type"));
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem) {
                return true;
            }
        }

        return (pSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                || pSource.is(DamageTypes.DRAGON_BREATH)
                || pSource.is(DamageTypes.FALLING_ANVIL)
                || pSource.is(DamageTypes.GENERIC)
                || pSource.is(DamageTypes.GENERIC_KILL)
                || pSource.is(DamageTypes.FELL_OUT_OF_WORLD))
                && super.hurt(pSource, pAmount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.STONE_HIT;
    }

    public void setType(int type) {
        this.entityData.set(TYPE, type);
    }

    public SCP173Types get173Type() {
        return SCP173Types.values()[this.entityData.get(TYPE)];
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getRandom().nextInt(5000) == 1 && !this.level().isClientSide) {
            BlockPos pos1 = new BlockPos((int) this.getX(), (int) this.getBoundingBox().minY, (int) this.getZ());
            BlockPos pos2 = new BlockPos((int) this.getX(), (int) (this.getBoundingBox().minY - 1), (int) this.getZ());
            if (this.level().getBlockState(pos1).isAir() && !(this.level().getBlockState(pos2).getBlock() instanceof SculptureExcrement)) {
                this.level().setBlockAndUpdate(pos1, SCPBlocks.SCULPTURE_EXCREMENT.get().defaultBlockState());
            }
        }
        boolean blink = false;
        List<ServerPlayer> toBlink = new ArrayList<>();

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(32))) {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity instanceof ServerPlayer serverPlayer && serverPlayer.isCreative()) continue;
                boolean hasLos = Vec3Helper.isInAngle(livingEntity, this.getEyePosition(), 90) && this.hasLineOfSight(livingEntity);
                if (hasLos) {
                    if (livingEntity instanceof ServerPlayer serverPlayer)
                        toBlink.add(serverPlayer);
                    blink = true;
                }
            }
        }

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(32))) {
            if (entity instanceof SCP131 scp131 && scp131.hasLineOfSight(this)) {
                break;
            } else {
                if (entity instanceof ServerPlayer player && !player.isCreative()) {
                    if (this.getTarget() != null) {
                        if (this.getTarget() != player) {
                            if (this.getTarget().distanceTo(this) > this.getTarget().distanceTo(entity)) {
                                this.setTarget(player);
                            }
                            continue;
                        }
                    } else {
                        this.setTarget(player);
                    }
                    if (blink && !player.hasEffect(MobEffects.BLINDNESS)) {
                        if (this.blinkTick + 100 < this.tickCount) {
                            this.blinkTick = this.tickCount;
                            toBlink.forEach(sPlayer -> ModMessages.sendToPlayer(new BlinkS2CPacket(true), sPlayer));
                            this.teleportTowards(player, 20);
                            this.checkAndPerformAttack(player, this.distanceToSqr(player));
                        }
                    } else {
                        if (this.blinkTick + 8 < this.tickCount) {
                            this.blinkTick = this.tickCount;
                            this.teleportTowards(player, 5);
                            this.checkAndPerformAttack(player, this.distanceToSqr(player));
                        }
                    }
                }
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (this.get173Type().equals(SCP173Types.ISOLATION)) {
            if (this.inEvent) return;
            boolean inRange = this.inRange(enemy, distToEnemySqr);
            if ((enemy instanceof ServerPlayer player && player.isAlive()) && (this.getRandom().nextInt(5) == 2 && inRange)) {
                this.inEvent = true;
                this.setPose(Pose.EMERGING);
                ModMessages.sendToPlayer(new PlayLocalSoundS2C(SCPSounds.SCP_173_STRANGLE_SEQUENCE.get()), player);
                ModMessages.sendToPlayer(new VignetteS2CPacket(140, false, false), player);
                RefractionMisc.enableMovement(player, 140);
                CameraShakeHandler.invokeCameraShakeToPlayer(140, 5, player);
                RunnableHandler.addRunnable(() -> player.lookAt(EntityAnchorArgument.Anchor.EYES, this.getEyePosition().add(0, 0.5F, 0)), 140);
                RunnableCooldownHandler.addDelayedRunnable(() -> {
                    this.doHurtTarget(player);
                    this.playSound(SCPSounds.SCP_173_STRANGLE_KILL.get());
                    this.setTarget(null);
                    this.inEvent = false;
                    this.setPose(Pose.STANDING);
                }, 140);
                this.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
                return;
            } else if (inRange && !enemy.isDeadOrDying()) {
                this.playKillSound();
                this.doHurtTarget(enemy);
            }
        }
        if (enemy.isDeadOrDying()) return;
        if (this.inRange(enemy, distToEnemySqr)) {
            this.doHurtTarget(enemy);
            this.playKillSound();
        }
    }

    protected boolean inRange(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + enemy.getBbWidth();
    }

    public void setPassable(boolean control) {
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(control);
        ((GroundPathNavigation) this.getNavigation()).setCanPassDoors(control);
    }

    public void teleportTowards(LivingEntity pTarget, int maxTeleport) {
        if (this.inEvent) return;
        if (pTarget == null) return;
        GroundPathNavigation pathNavigation = (GroundPathNavigation) this.getNavigation();

        // Regular SCP-173 teleport
        this.setPassable(false);
        Path path = pathNavigation.createPath(pTarget, 0);
        if (path != null && path.getNodeCount() >= path.getDistToTarget()) { // Mostly prevents 173 from getting out of the containment area.
            this.lookAt(EntityAnchorArgument.Anchor.EYES, pTarget.position());
            int nodeToTP = Mth.lerpInt((float) Math.min(path.getNodeCount(), 25) / 32, 0, 20);
            Node node = path.getNode(nodeToTP);
            this.teleport(node.x, node.y, node.z, pTarget);
        }

        // Door navigation
        if (maxTeleport < 20) {
            this.setPassable(true);
            path = pathNavigation.createPath(pTarget, 0);
            if (path != null && path.getNodeCount() >= path.getDistToTarget()) {
                BlockPos doorPos = BlockPos.ZERO;
                boolean hasDoor = false;
                if (!path.isDone()) {
                    for (int i = 0; i < path.getNodeCount(); ++i) {
                        Node node1 = path.getNode(i);
                        doorPos = new BlockPos(node1.x, node1.y + 1, node1.z);
                        if (this.level().getBlockState(doorPos).getBlock() instanceof DoorBlock && DoorBlock.isWoodenDoor(this.level().getBlockState(doorPos))) {
                            hasDoor = true;
                            break;
                        }
                    }
                }
                if (hasDoor) {
                    BlockState blockState = this.level().getBlockState(doorPos);
                    BlockPos facingPos = doorPos.relative(blockState.getValue(DoorBlock.FACING), 1);
                    BlockPos relativeDoorPos = BlockPos.containing(facingPos.getX() - doorPos.getX() == 0 ? 0 : 1, 0, 0);
                    BlockPos relativeTargetPos = BlockPos.containing(pTarget.getX() - doorPos.getX() < 0 ? 0 : 1, 0, pTarget.getZ() - doorPos.getZ() < 0 ? 0 : 1);
                    boolean tpXDoor = relativeDoorPos.getX() > 0;
                    boolean tpXTarget = relativeTargetPos.getX() > 0;
                    boolean tpZTarget = relativeTargetPos.getZ() > 0;
                    Direction direction = blockState.getValue(DoorBlock.FACING);
                    direction = direction == Direction.WEST ? Direction.EAST : direction == Direction.NORTH ? Direction.SOUTH : direction;
                    int tpDist = tpXDoor ? tpXTarget ? 2 : -2 : tpZTarget ? 2 : -2;
                    doorPos = doorPos.relative(direction, tpDist);
                    this.lookAt(EntityAnchorArgument.Anchor.EYES, pTarget.position());
                    this.teleport(doorPos.getX() + 0.5F, doorPos.getY(), doorPos.getZ() + 0.5F, pTarget);
                }
            }
        }
    }

    private void teleport(double pX, double pY, double pZ, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            this.updateTp(player);
        }
        this.playMoveSounds();
        this.setPos(new Vec3(pX, pY, pZ));
    }

    public void updateTp(ServerPlayer player) {
        if (this.distanceTo(player) <= 3.0F) {
            this.teleports.putIfAbsent(player, 0);
            this.teleports.put(player, this.teleports.get(player) + 1);
            SoundUtil.playLocalSound(player, SCPSounds.SCP_173_HORROR.get());
        }
    }

    public void playKillSound() {
        this.playSound(SCPSounds.SCP_173_KILL.get());
    }

    public void playMoveSounds() {
        this.playSound(SCPSounds.SCP_173_MOVE.get());
    }

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("scp_173i_idle");
    public static final RawAnimation STRANGLE = RawAnimation.begin().thenLoop("scp_173i_strangle");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 0, state -> {
            if (this.get173Type().equals(SCP173Types.ISOLATION))
                if (this.getPose().equals(Pose.EMERGING)) {
                    state.setAndContinue(STRANGLE);
                } else {
                    state.setAndContinue(IDLE);
                }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    public enum SCP173Types {
        CLASSIC("", "SCP-173: Classic"),
        UNITY("u", "SCP-173: Unity"),
        REBORN("r", "SCP-173: Reborn"),
        ISOLATION("i", "SCP-173: Isolation");

        public final String id;
        public final String fullName;

        SCP173Types(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public String getId() {
            return this.id;
        }
    }
}
