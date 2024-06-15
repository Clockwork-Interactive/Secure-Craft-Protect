package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.goals.SCP811AttackGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class SCP811 extends TamableAnimal implements GeoEntity, NeutralMob, Anomaly {
    private static final EntityDataAccessor<Boolean> DATA_HAS_TARGET = SynchedEntityData.defineId(SCP811.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(SCP811.class, EntityDataSerializers.BOOLEAN);

    public static final RawAnimation SIT_ANIM = RawAnimation.begin().thenPlay("811_sit");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final HashMap<Integer, Triple<String, RawAnimation, Predicate<AnimationState<?>>>> animations = new HashMap<>();

    public SCP811(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractFish.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Spider.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Silverfish.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Frog.class, true));
        // Tame goals
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SCP811AttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0F)
                .add(Attributes.ATTACK_SPEED, 0.5F)
                .add(Attributes.MAX_HEALTH, 30.0F)
                .add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2F)
                .add(ForgeMod.SWIM_SPEED.get(), 3.0F);
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        super.setTarget(pTarget);
        this.setHasTarget(pTarget != null);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(DATA_HAS_TARGET, false);
    }

    public void setHasTarget(boolean hasTarget) {
        this.entityData.set(DATA_HAS_TARGET, hasTarget);
    }

    public boolean hasTarget() {
        return this.entityData.get(DATA_HAS_TARGET);
    }

    @Override
    public void tick() {
        if (this.getRandom().nextFloat() > 0.9999F) {
            BlockState blockState = this.level().getBlockState(this.getOnPos());
            if (blockState.getBlock() instanceof BonemealableBlock bonemealableblock) {
                if (bonemealableblock.isValidBonemealTarget(this.level(), this.getOnPos(), blockState, this.level().isClientSide)) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (bonemealableblock.isBonemealSuccess(this.level(), this.getRandom(), this.getOnPos(), blockState)) {
                            bonemealableblock.performBonemeal(serverLevel, this.getRandom(), this.getOnPos(), blockState);
                        }
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean wantsToPickUp(ItemStack pStack) {
        FoodProperties foodProperties = pStack.getFoodProperties(this);
        return pStack.isEdible() && foodProperties != null && foodProperties.isMeat();
    }

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {
        ItemStack stack = pItemEntity.getItem();
        if (!stack.getItem().getDescriptionId().contains("cooked")) { // Very hacky
            int slimeballs = 0;
            for (int x = 0; x < stack.getCount(); x++) {
                slimeballs += this.getRandom().nextIntBetweenInclusive(1, 2);
            }
            if (slimeballs > 0) {
                ItemStack slimeballStack = new ItemStack(Items.SLIME_BALL, slimeballs);
                this.drop(slimeballStack, false);
            }
            stack.shrink(stack.getCount());
        }
        super.pickUpItem(pItemEntity);
    }

    public void drop(ItemStack pDroppedItem, boolean pDropAround) {
        if (this.level().isClientSide) {
            this.swing(InteractionHand.MAIN_HAND);
        }

        double d0 = this.getEyeY() - (double) 0.3F;
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), d0, this.getZ(), pDroppedItem);
        itementity.setPickUpDelay(40);

        if (pDropAround) {
            float f = this.random.nextFloat() * 0.5F;
            float f1 = this.random.nextFloat() * ((float) Math.PI * 2F);
            itementity.setDeltaMovement(-Mth.sin(f1) * f, 0.2F, Mth.cos(f1) * f);
        } else {
            float f8 = Mth.sin(this.getXRot() * ((float) Math.PI / 180F));
            float f2 = Mth.cos(this.getXRot() * ((float) Math.PI / 180F));
            float f3 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
            float f4 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
            float f5 = this.random.nextFloat() * ((float) Math.PI * 2F);
            float f6 = 0.02F * this.random.nextFloat();
            itementity.setDeltaMovement((double) (-f3 * f2 * 0.3F) + Math.cos(f5) * (double) f6, -f8 * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F, (double) (f4 * f2 * 0.3F) + Math.sin(f5) * (double) f6);
        }
        this.level().addFreshEntity(itementity);
    }

    /**
     * Tame stuff
     */

    public void setTame(boolean pTamed) {
        super.setTame(pTamed);
        if (pTamed) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0);
            this.setHealth(20.0F);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(8.0);
        }

        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(4.0);
    }

    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            if (this.isTame() && this.isOwnedBy(pPlayer)) {
                return InteractionResult.SUCCESS;
            } else {
                return !itemstack.is(SCPItems.HAIRBRUSH.get()) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(pPlayer)) {
                    if (itemstack.isEdible() && itemstack.getFoodProperties(pPlayer).isMeat() && !pPlayer.getCooldowns().isOnCooldown(item)) {
                        this.playSound(SoundEvents.GENERIC_EAT);
                        this.spawnAtLocation(Items.SLIME_BALL);
                        this.usePlayerItem(pPlayer, pHand, itemstack);
                        pPlayer.getCooldowns().addCooldown(item, 40);
                        if (this.getHealth() < this.getMaxHealth()) this.heal((float) Objects.requireNonNull(itemstack.getFoodProperties(this)).getNutrition());
                        return InteractionResult.CONSUME;
                    }

                    InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                    if (!interactionresult.consumesAction() && !this.isOrderedToSit() || this.isBaby()) {
                        setSitting(true);
                        pPlayer.displayClientMessage(Component.literal("SCP-811 Is now Sitting"), true);
                    } else if (!interactionresult.consumesAction() && this.isOrderedToSit() || this.isBaby()) {
                        setSitting(false);
                        pPlayer.displayClientMessage(Component.literal("SCP-811 Is now Following"), true);
                    }

                    return interactionresult;
                }
            } else if (itemstack.is(SCPItems.HAIRBRUSH.get())) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                    this.tame(pPlayer);
                    setSitting(true);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }

                this.setPersistenceRequired();
                return InteractionResult.SUCCESS;
            }
            InteractionResult interactionresult1 = super.mobInteract(pPlayer, pHand);
            if (interactionresult1.consumesAction()) {
                this.setPersistenceRequired();
            }
            return interactionresult1;
        }
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_811;
    }

    private boolean isCurrentAnimation(AnimationState<?> state) {
        return !state.isCurrentAnimation(SIT_ANIM) || state.getController().hasAnimationFinished();
    }

    @Override
    public boolean doArmAnimations(AnimationState<?> state) {
        return this.isCurrentAnimation(state);
    }

    @Override
    public boolean doLegAnimations(AnimationState<?> state) {
        return this.isCurrentAnimation(state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        this.addDefault("none", RawAnimation.begin());
        this.addContinuous(0, "811_sit", SIT_ANIM, (state) -> this.isSitting() && !this.hasTarget());
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> PlayState.STOP)
                .triggerableAnim("811_sit", SIT_ANIM)
                .triggerableAnim("none", RawAnimation.begin()));
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isSitting", this.isSitting());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setSitting(pCompound.getBoolean("isSitting"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public HashMap<Integer, Triple<String, RawAnimation, Predicate<AnimationState<?>>>> getAnimations() {
        return animations;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }
}