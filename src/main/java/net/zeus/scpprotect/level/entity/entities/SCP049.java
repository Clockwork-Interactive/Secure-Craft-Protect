package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.refractionapi.refraction.vfx.VFXHelper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.entity.goals.SCP049AttackGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.util.Misc;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP049 extends SCPEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final EntityDataAccessor<Boolean> DATA_DRUGGED = SynchedEntityData.defineId(SCP049.class, EntityDataSerializers.BOOLEAN);
    private int friendlyTimer = Misc.TPS * 10;
    private int druggedTimer = Misc.TPS * 30;

    public SCP049(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SCP049AttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, Double.MAX_VALUE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DRUGGED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Drugged", this.isDrugged());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setDrugged(pCompound.getBoolean("Drugged"));
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(SCPItems.LAVENDER.get()) && this.isAggressive()) {
            this.setDrugged(true);
            this.setAggressive(false);
            stack.shrink(1);
            pPlayer.swing(pHand);
            VFXHelper.summonParticlesAroundEntity(this, ParticleTypes.HEART, 4);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8))) {
            if (entity instanceof Player player && !(player.isCreative() || player.isSpectator()) || entity instanceof Villager) {
                if (!entity.hasEffect(SCPEffects.PESTILENCE.get()) && !this.isDrugged()) {
                    if (this.friendlyTimer > 0) {
                        this.friendlyTimer--;
                    } else {
                        if (this.random.nextFloat() > 0.99F) {
                            if (entity instanceof Player player) {
                                SCPAdvancements.grant(player, SCPAdvancements.GOOD_HEAVENS);
                            }
                            entity.addEffect(new MobEffectInstance(SCPEffects.PESTILENCE.get(), -1));
                        }
                    }
                }
            }
        }
        if (this.isDrugged()) {
            if (this.druggedTimer > 0) {
                this.druggedTimer--;
            } else {
                this.druggedTimer = Misc.TPS * 30;
                this.setDrugged(false);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity source = pSource.getEntity();
        if (source instanceof LivingEntity entity) {
            entity.addEffect(new MobEffectInstance(SCPEffects.PESTILENCE.get(), -1));
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void awardKillScore(Entity pKilled, int pScoreValue, DamageSource pSource) {
        this.playSound(SCPSounds.SCP_049_KILL.get());
        this.playSound(SCPSounds.SCP_049_RESURRECT.get());
        this.friendlyTimer = Misc.TPS * 10;
        if (pKilled instanceof Player) {
            SCP049_2 scp049_2 = AnomalyRegistry.SCP_049_2.create(this.level(), pKilled.position());
            this.level().addFreshEntity(scp049_2);
        } else if (pKilled instanceof Villager) {
            ZombieVillager zombieVillager = EntityType.ZOMBIE_VILLAGER.create(this.level());
            zombieVillager.moveTo(pKilled.getX(), pKilled.getY(), pKilled.getZ());
            this.level().addFreshEntity(zombieVillager);
        }
        super.awardKillScore(pKilled, pScoreValue, pSource);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAggressive() ? SCPSounds.SCP_049_ATTACK_FAIL.get() :  super.getAmbientSound();
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    public boolean isDrugged() {
        return this.entityData.get(DATA_DRUGGED);
    }

    public void setDrugged(boolean drugged) {
        this.entityData.set(DATA_DRUGGED, drugged);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_049;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean doArmAnimations(AnimationState<?> state) {
        return !this.isAggressive();
    }

    @Override
    public boolean doLegAnimations(AnimationState<?> state) {
        return true;
    }

    @Override
    public boolean doHeadAnimation(AnimationState<?> state) {
        return true;
    }
}
