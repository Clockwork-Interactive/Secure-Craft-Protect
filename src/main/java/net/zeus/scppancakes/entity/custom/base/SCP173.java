package net.zeus.scppancakes.entity.custom.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.zeus.scppancakes.block.ModBlocks;
import net.zeus.scppancakes.block.custom.SculptureExcrement;
import net.zeus.scppancakes.custom.RunnableCooldownHandler;
import net.zeus.scppancakes.entity.custom.goals.SCP173AttackGoal;
import net.zeus.scppancakes.networking.ModMessages;
import net.zeus.scppancakes.networking.packet.BlinkS2CPacket;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Predicate;

public class SCP173 extends Monster implements GeoEntity, NeutralMob {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int savedBlinkTick = 0;
    public final int blinkRange = 40;
    public int blinkRange173 = this.blinkRange * 10;
    public boolean canMove = true;
    //private static final DamageSource scp173DamageSource = new DamageSource("scppancakes.scp173i.damage");

    public SCP173(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new SCP173AttackGoal(this, 0.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.setPersistenceRequired();
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

    public static final RawAnimation IDLE173i = RawAnimation.begin().thenLoop("scp_173i_idle");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 0, state -> {
            state.setAndContinue(IDLE173i);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player player) {

            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem) {
                this.hurt(this.level.damageSources().generic(), 10.0F);
                return false;
            }
        }

        if (pSource.is(DamageTypes.MAGIC) || pSource.is(DamageTypes.DROWN) || pSource.is(DamageTypes.IN_WALL) || pSource.is(DamageTypes.FALL) || pSource.is(DamageTypes.EXPLOSION)) {
            return false;
        } else {
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public void tick() { // TODO create targeting method
        if (!this.level.isClientSide) {
            AABB blinkRange = new AABB(this.getX() - this.blinkRange173, this.getY() - this.blinkRange173, this.getZ() - this.blinkRange173, this.getX() + this.blinkRange173, this.getY() + this.blinkRange173, this.getZ() + this.blinkRange173);
            for (Entity entity : this.level.getEntities(this, blinkRange, (val) -> true)) {
                if (!this.level.isClientSide) {
                    if (entity != null) {
                        if (entity instanceof Player player) {
                            boolean hasLoS = this.hasLineOfSight173(player, this.blinkRange173);
                            if (hasLoS) {
                                if (this.savedBlinkTick + 100 < this.tickCount) {
                                    this.savedBlinkTick = this.tickCount;
                                    this.canMove = true;
                                    RunnableCooldownHandler.addDelayedRunnable(() -> this.canMove = false, 15);
                                    ModMessages.sendToPlayer(new BlinkS2CPacket(true), (ServerPlayer) player);
                                }
                            } else {
                                this.canMove = true;
                            }
                        }
                    }
                }
            }
        }
        if (this.getRandom().nextInt(5000) == 1 && !this.level.isClientSide) {
            BlockPos pos1 = new BlockPos((int) this.getX(), (int) this.getBoundingBox().minY, (int) this.getZ());
            BlockPos pos2 = new BlockPos((int) this.getX(), (int) (this.getBoundingBox().minY - 1), (int) this.getZ());
            Material material1 = this.level.getBlockState(pos1).getMaterial();
            if (material1 == Material.AIR && !(this.level.getBlockState(pos2).getBlock() instanceof SculptureExcrement)) {
                this.level.setBlockAndUpdate(pos1, ModBlocks.SCULPTURE_EXCREMENT.get().defaultBlockState());
            }
        }
        super.tick();
    }

    public boolean hasLineOfSight173(Player player, int distance) {
        if (player != null) {
            Vec3 vec3 = player.getEyePosition();
            for (int x = 0; x < distance; x++) {
                Vec3 vec31 = this.calculateViewVector(player.getXRot(), player.getYRot()).scale(x);
                Vec3 vec32 = vec3.add(vec31);
                if (player.level.getBlockState(BlockPos.containing(vec32.x, vec32.y, vec32.z)).isAir()) {
                    AABB aabb = player.getBoundingBox().expandTowards(vec31).inflate(1.0D);
                    double i = x * x;
                    Predicate<Entity> predicate = (entity) -> !entity.isSpectator() && entity.isPickable();
                    EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(player, vec3, vec32, aabb, predicate, i);
                    if (entityhitresult != null) {
                        return (vec3.distanceToSqr(entityhitresult.getLocation()) < i);
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * Teleport 173 towards the entity.
     */
    public void teleportTowards(Entity pTarget) {
        if (this.distanceTo(pTarget) > 5) {
            Vec3 vec3 = new Vec3(this.getX() - pTarget.getX(), this.getY(0.5D) - pTarget.getEyeY(), this.getZ() - pTarget.getZ());
            vec3 = vec3.normalize();
            double d1 = (this.getX() - vec3.x);
            double d2 = this.getY() + ((double) (this.random.nextInt(8) - 8)) * 2;
            double d3 = (this.getZ() - vec3.z);
            this.teleport(d1, d2, d3);
        } else {
            this.teleport(pTarget.getX(), pTarget.getY(), pTarget.getZ());
        }
    }

    private boolean teleport(double pX, double pY, double pZ) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while (blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.getMaterial().blocksMotion();
        if (flag) {
            Vec3 vec3 = new Vec3(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
            boolean flag2 = this.randomTeleport(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), false);
            if (flag2) {
                this.setPos(new Vec3(pX, this.getTarget().getY(), pZ));
                this.level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
            }
            return flag2;
        } else {
            return false;
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}