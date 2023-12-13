package net.zeus.scpprotect.level.entity.custom.base;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.ModBlocks;
import net.zeus.scpprotect.level.block.custom.SculptureExcrement;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.ModSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.BlinkS2CPacket;
import net.zeus.scpprotect.networking.S2C.PlayLocalSoundS2C;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCP173 extends Monster implements GeoEntity, Anomaly {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int blinkTick = 0;
    public final HashMap<Player, Integer> teleports = new HashMap<>();

    protected SCP173(EntityType<? extends Monster> pEntityType, Level pLevel) {
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
    public void tick() {
        super.tick();

        if (this.getRandom().nextInt(5000) == 1 && !this.level().isClientSide) {
            BlockPos pos1 = new BlockPos((int) this.getX(), (int) this.getBoundingBox().minY, (int) this.getZ());
            BlockPos pos2 = new BlockPos((int) this.getX(), (int) (this.getBoundingBox().minY - 1), (int) this.getZ());
            if (this.level().getBlockState(pos1).isAir() && !(this.level().getBlockState(pos2).getBlock() instanceof SculptureExcrement)) {
                this.level().setBlockAndUpdate(pos1, ModBlocks.SCULPTURE_EXCREMENT.get().defaultBlockState());
            }
        }
        boolean blink = false;
        List<ServerPlayer> toBlink = new ArrayList<>();

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(32))) {
            if (entity instanceof ServerPlayer player && !player.isCreative()) {
                boolean hasLos = Vec3Helper.isInAngle(player, this.blockPosition(), 90) && this.hasLineOfSight(player);
                if (hasLos) {
                    toBlink.add(player);
                    blink = true;
                }
            }
        }

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(32))) {
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

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (enemy.isDeadOrDying()) return;
        if (this.inRange(enemy, distToEnemySqr)) {
            this.doHurtTarget(enemy);
            this.playKillSound();
        }
    }

    protected boolean inRange(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + enemy.getBbWidth();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem) {
                this.hurt(this.level().damageSources().generic(), 10.0F);
                return true;
            }
        }

        return !(pSource.is(DamageTypes.MAGIC) || pSource.is(DamageTypes.DROWN) || pSource.is(DamageTypes.IN_WALL) || pSource.is(DamageTypes.FALL) || pSource.is(DamageTypes.EXPLOSION)) && super.hurt(pSource, pAmount);
    }

    public void setPassable(boolean control) {
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(control);
        ((GroundPathNavigation) this.getNavigation()).setCanPassDoors(control);
    }

    public void teleportTowards(LivingEntity pTarget, int maxTeleport) {
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
        this.teleports.putIfAbsent(player, 0);
        this.teleports.put(player, this.teleports.get(player) + 1);
        switch (this.teleports.get(player)) {
            case 1 ->
                    ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_HORROR_1.get()), player);
            case 2 ->
                    ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_HORROR_2.get()), player);
            case 3 ->
                    ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_HORROR_3.get()), player);
            case 4 ->
                    ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_HORROR_4.get()), player);
            case 5 ->
                    ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_HORROR_5.get()), player);
        }
    }

    public void playKillSound() {
        SoundEvent[] soundEvents = new SoundEvent[]{ModSounds.SCP_173_KILL_1.get(), ModSounds.SCP_173_KILL_2.get()};
        this.playSound(soundEvents[this.getRandom().nextInt(0, 1)]);
    }

    public void playMoveSounds() {
        SoundEvent[] soundEvents = new SoundEvent[]{ModSounds.SCP_173_MOVE_1.get(), ModSounds.SCP_173_MOVE_2.get(), ModSounds.SCP_173_MOVE_3.get()};
        this.playSound(soundEvents[this.getRandom().nextInt(0, 2)]);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

}
