package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.data.DeobfuscatedUtil;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.entities.goals.SCP939ListenTargetGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.util.Misc;
import net.zeus.scpprotect.util.ModDamageTypes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SCP939 extends Monster implements GeoEntity, Anomaly {

    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final float speedModifier = 0.35F;
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier("Attacking speed boost 939", speedModifier, AttributeModifier.Operation.ADDITION);
    private final Map<Class<? extends Mob>, SoundEvent> soundCache = new HashMap<>();
    private final List<Class<? extends Mob>> invalidSoundCache = new ArrayList<>();
    private final List<SoundEvent> ATTACK_SOUNDS = Arrays.asList(SCPSounds.SCP_939_ATTACK_1.get(), SCPSounds.SCP_939_ATTACK_2.get(), SCPSounds.SCP_939_ATTACK_3.get(), SCPSounds.SCP_939_ATTACK_4.get());
    private final List<SoundEvent> BABY_IDLE_SOUNDS = Arrays.asList(SCPSounds.SCP_939_BABY_IDLE_1.get(), SCPSounds.SCP_939_BABY_IDLE_2.get(), SCPSounds.SCP_939_BABY_IDLE_3.get());
    private final List<SoundEvent> HURT_SOUNDS = Arrays.asList(SCPSounds.SCP_939_HURT_1.get(), SCPSounds.SCP_939_HURT_2.get());
    private final SoundEvent SCREECH_SOUND = SCPSounds.SCP_939_SCREECH.get();
    private final SoundEvent SPOT_TARGET_SOUND = SCPSounds.SCP_939_SPOT_TARGET.get();

    public SCP939(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new SCP939ListenTargetGoal(this));
        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 20.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 35.0F)
                .add(Attributes.FOLLOW_RANGE, 20.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
                .add(ForgeMod.SWIM_SPEED.get(), 2.5F)
                .add(ForgeMod.ENTITY_REACH.get(), 5.0F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 3.0F);
    }

    @Override
    public void setTarget(@javax.annotation.Nullable LivingEntity pLivingEntity) {
        AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (pLivingEntity == null) {
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
                this.playSound(this.SPOT_TARGET_SOUND);
            }
        }

        super.setTarget(pLivingEntity);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (this.getTarget() != null) {
            super.playStepSound(pPos, pState);
        }
    }

    @Override
    public void tick() {
        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(35))) {
            if (entity instanceof Mob mob) {
                if (!this.level().isClientSide && this.soundCache.get(mob.getClass()) == null && !this.invalidSoundCache.contains(mob.getClass())) {
                    try {
                        Method ambientSound = DeobfuscatedUtil.MobAmbientSound;
                        ambientSound.setAccessible(true);
                        SoundEvent event = (SoundEvent) ambientSound.invoke(mob);
                        if (event != null) {
                            this.soundCache.put(mob.getClass(), event);
                        } else {
                            this.invalidSoundCache.add(mob.getClass());
                        }

                    } catch (InvocationTargetException | IllegalAccessException e) {
                        SCP.LOGGER.error("Could not fetch mob sound \n {}", e.getMessage());
                        this.invalidSoundCache.add(mob.getClass());
                    }
                }
            }
        }

        if (!this.soundCache.isEmpty()) {
            if (RandomSource.create().nextFloat() > 0.995F && this.soundCache.values().stream().findFirst().isPresent()) {
                SoundEvent soundEvent = this.soundCache.values().stream().findFirst().get();
                if (this.soundCache.size() > 1) {
                    for (SoundEvent event : this.soundCache.values()) {
                        if (RandomSource.create().nextFloat() > 0.7F) {
                            soundEvent = event;
                            break;
                        }
                    }
                }
                this.playSound(soundEvent);
            }
        }

        if (!this.level().isClientSide) {
            if (this.getTarget() instanceof Player player) {
                if (RandomSource.create().nextFloat() > 0.99F) {
                    AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
                    cloud.setDuration(100);
                    cloud.setOwner(this);
                    cloud.setRadius(4.0F);
                    cloud.addEffect(new MobEffectInstance(SCPEffects.AMNESIA.get(), 160, 0));
                    cloud.setInvisible(true);
                    cloud.setWaitTime(0);
                    this.level().addFreshEntity(cloud);
                }
                MinecraftServer server = this.level().getServer();
                if (server != null && !server.isSingleplayer()) {
                    if (!this.hasCustomName()) {
                        if (!player.level().players().isEmpty()) {
                            this.setCustomName(player.level().players().get(RandomSource.create().nextInt(player.level().players().size())).getName());
                        }
                    }
                    this.setCustomNameVisible(true);
                }

            } else {
                this.setCustomName(null);
            }

        }

        super.tick();
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof Player player) {
            player.addEffect(new MobEffectInstance(SCPEffects.AMNESIA.get(), 160, 0, false, false, false));
        }
        if (pEntity instanceof LivingEntity living && living.isAlive())
            this.playSound(RefractionMisc.getRandom(ATTACK_SOUNDS));
        pEntity.hurt(Misc.damageSource(ModDamageTypes.SCP939_DAMAGE, pEntity.level()), 10.0F);
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.playSound(RefractionMisc.getRandom(HURT_SOUNDS));
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean dampensVibrations() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }
}
