package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.util.Misc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP131 extends TamableAnimal implements Anomaly, GeoEntity {
    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SCP131.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SCP131(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.MAX_HEALTH, 23.0F)
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

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, SCP173.class, 10.0F));

        this.goalSelector.addGoal(5, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new OwnerHurtTargetGoal(this));

        super.registerGoals();
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    /**
     * Variant stuff
     */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(pCompound.getInt("Variant"));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SCPSounds.SCP_131_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SCPSounds.SCP_131_HURT.get();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        int variant = 2;
        if (stack.is(Items.RED_DYE) && this.getVariant() != 0) {
            variant = 0;
        }
        if (stack.is(Items.YELLOW_DYE) && this.getVariant() != 1) {
            variant = 1;
        }
        if (variant != 2) {
            this.setVariant(variant);
            this.playSound(SoundEvents.DYE_USE);
            Misc.summonParticlesAroundEntity(this, ParticleTypes.HAPPY_VILLAGER, 10);
            stack.shrink(1);
            pPlayer.swing(pHand);
        }
        if (!this.isTame() && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
            this.tame(pPlayer);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
            return InteractionResult.SUCCESS;
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }

        InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
        if (this.isOwnedBy(pPlayer) && !stack.is(Items.RED_DYE) || !stack.is(Items.YELLOW_DYE)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            String isSittingText;
            if (this.isOrderedToSit()) {
                isSittingText = "Is Sitting";
            } else {
                isSittingText = "Is Roaming";
            }
            pPlayer.displayClientMessage(Component.literal("SCP-131 " + isSittingText), true);
            this.jumping = false;
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            return InteractionResult.SUCCESS;
        } else {
            return interactionresult;
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setVariant(this.random.nextInt(0, 4));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
