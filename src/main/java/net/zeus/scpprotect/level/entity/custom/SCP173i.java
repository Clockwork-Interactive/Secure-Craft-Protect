package net.zeus.scpprotect.level.entity.custom;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.refractionapi.refraction.camerashake.CameraShakeHandler;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.refractionapi.refraction.runnable.RunnableCooldownHandler;
import net.refractionapi.refraction.runnable.RunnableHandler;
import net.zeus.scpprotect.level.entity.custom.base.SCP173;
import net.zeus.scpprotect.level.sound.ModSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.PlayLocalSoundS2C;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class SCP173i extends SCP173 {

    public boolean inEvent = false;

    public SCP173i(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void teleportTowards(LivingEntity pTarget, int maxTeleport) {
        if (this.inEvent) return;
        super.teleportTowards(pTarget, maxTeleport);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (this.inEvent) return;
        boolean inRange = this.inRange(enemy, distToEnemySqr);
        if ((enemy instanceof ServerPlayer player && player.isAlive()) && (this.getRandom().nextInt(5) == 2 && inRange)) {
            this.inEvent = true;
            this.setPose(Pose.EMERGING);
            ModMessages.sendToPlayer(new PlayLocalSoundS2C(ModSounds.SCP_173_STRANGLE_SEQUENCE.get()), player);
            ModMessages.sendToPlayer(new VignetteS2CPacket(140, false, false), player);
            RefractionMisc.enableMovement(player, 140);
            CameraShakeHandler.invokeCameraShakeToPlayer(140, 5, player);
            RunnableHandler.addRunnable(() -> player.lookAt(EntityAnchorArgument.Anchor.EYES, this.getEyePosition().add(0,0.5F,0)), 140);
            RunnableCooldownHandler.addDelayedRunnable(() -> {
                this.doHurtTarget(player);
                this.playSound(ModSounds.SCP_173_STRANGLE_KILL.get());
                this.setTarget(null);
                this.inEvent = false;
                this.setPose(Pose.STANDING);
            }, 140);
            this.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
        } else if (inRange && !enemy.isDeadOrDying()) {
            this.playKillSound();
            this.doHurtTarget(enemy);
        }
    }

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("scp_173i_idle");
    public static final RawAnimation STRANGLE = RawAnimation.begin().thenLoop("scp_173i_strangle");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 0, state -> {
            if (this.getPose().equals(Pose.EMERGING)) {
                state.setAndContinue(STRANGLE);
            } else {
                state.setAndContinue(IDLE);
            }
            return PlayState.CONTINUE;
        }));
    }
}