package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.vfx.VFXHelper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.util.Misc;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;

public class SCP111 extends Animal implements GeoEntity, Anomaly {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SCP111.class, EntityDataSerializers.INT);
    private int growTimer = Misc.TPS * 120;
    private final HashMap<Item, Integer> dyeMap = new HashMap<>() {{
        put(Items.RED_DYE, 0);
        put(Items.BLUE_DYE, 1);
        put(Items.GREEN_DYE, 2);
        put(Items.YELLOW_DYE, 3);
    }};

    public SCP111(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

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

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        int variant = this.dyeMap.getOrDefault(stack.getItem(), -1);
        if (variant != -1 && this.getVariant() != variant) {
            this.setVariant(variant);
            this.playSound(SoundEvents.DYE_USE);
            VFXHelper.summonParticlesAroundEntity(this, ParticleTypes.HAPPY_VILLAGER, 10);
            stack.shrink(1);
            pPlayer.swing(pHand);
        }
        if (stack.is(Items.BONE_MEAL)) {
            if (!this.level().isClientSide) {
                SmallFireball fireball = new SmallFireball(this.level(), this, this.getX(), this.getY() + 2.0F, this.getZ());
                fireball.shootFromRotation(this, this.getXRot(), this.getYRot(), 1.0F, 5.0F, 0.0F);
                VFXHelper.summonParticlesAroundEntity(this, ParticleTypes.FLAME, 10);
                this.level().addFreshEntity(fireball);
            }
            stack.shrink(1);
            pPlayer.swing(pHand);
            this.playSound(SoundEvents.FIRECHARGE_USE);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        BlockState blockBelow = this.level().getBlockState(this.getOnPos());
        if (this.growTimer > 0)
            this.growTimer--;
        if (blockBelow.getBlock() instanceof BonemealableBlock bonemealableBlock) {
            if (bonemealableBlock.isValidBonemealTarget(level(), this.getOnPos(), blockBelow, level().isClientSide)) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    if (bonemealableBlock.isBonemealSuccess(level(), this.getRandom(), this.getOnPos(), blockBelow)) {
                        if (this.growTimer <= 0) {
                            bonemealableBlock.performBonemeal(serverLevel, this.getRandom(), this.getOnPos(), blockBelow);
                            this.growTimer = Misc.TPS * 120;
                            this.playSound(SoundEvents.BONE_MEAL_USE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setVariant(this.random.nextInt(0, 4));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SCPSounds.SCP_111_IDLE.get();
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height * 0.7F;
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
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
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_111;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
