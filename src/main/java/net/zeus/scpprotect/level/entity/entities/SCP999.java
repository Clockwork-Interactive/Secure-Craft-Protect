package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class SCP999 extends Animal implements Anomaly, Bucketable, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SCP999(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.MAX_HEALTH, 23.0F)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.ATTACK_KNOCKBACK, 0)
                .add(Attributes.ATTACK_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0f));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1f, 1.0F, 10.0F));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.2f));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1f));

        this.targetSelector.addGoal(0, new MeleeAttackGoal(this, 1f, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.goalSelector.addGoal(15, new LookAtPlayerGoal(this, Player.class, 10.0F));

        super.registerGoals();
    }

    @Override
    public void playerTouch(Player pPlayer) {
        Map<MobEffect, MobEffectInstance> effects = pPlayer.getActiveEffectsMap();
        effects.entrySet().removeIf(mobEffectMobEffectInstanceEntry -> !mobEffectMobEffectInstanceEntry.getKey().isBeneficial());
        pPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1, true, true));
        super.playerTouch(pPlayer);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SLIME_JUMP_SMALL;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT_SMALL;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("scp_999_idle", Animation.LoopType.LOOP);
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().then("scp_999_movement", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "Controller", 0, state -> {
            if (!state.isMoving()) {
                state.getController().setAnimation(IDLE_ANIM);
            } else if (state.isMoving()) {
                state.getController().setAnimation(WALK_ANIM);
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean pFromBucket) {

    }

    @Override
    public void saveToBucketTag(ItemStack pStack) {
        Bucketable.saveDefaultDataToBucketTag(this, pStack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(SCPItems.SCP_999_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
