package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.event.CommonForgeEvents;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.goals.SCP966LookForPlayerGoal;
import net.zeus.scpprotect.level.entity.goals.SCP966PanicGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP966 extends SCPEntity {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SCP966(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new SCP966LookForPlayerGoal(this, Player.class, false));
        this.addBehaviourGoals();
        this.setPersistenceRequired();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return !pSource.is(DamageTypes.MAGIC)
                && super.hurt(pSource, pAmount);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SCPSounds.SCP_966_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SCPSounds.SCP_966_DEATH.get();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new SCP966PanicGoal(this, 1.5F));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 20.0F)
                .add(Attributes.ATTACK_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 200.0F)
                .add(Attributes.FOLLOW_RANGE, 20.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
                .add(ForgeMod.ENTITY_REACH.get(), 5.0F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 3.0F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public void tick() {
        super.tick();

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(12))) {
            if (entity instanceof LivingEntity livingEntity) {
                if (this.tickCount % 500 == 0) {
                    livingEntity.addEffect(new MobEffectInstance(SCPEffects.INSOMNIA.get(), -1, 0, false, true, true));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200, 1, false, true, true));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200, 1, false, true, true));
                    if (livingEntity instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(new VignetteS2CPacket(CommonForgeEvents.SCP_966Max, false, true), serverPlayer);
                    }
                }
            }
        }

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_966;
    }

}