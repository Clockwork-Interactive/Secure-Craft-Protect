package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.entities.SCP049;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class SCP049AttackGoal extends MeleeAttackGoal {
    private final SCP049 scp049;

    public SCP049AttackGoal(SCP049 scp049, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(scp049, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.scp049 = scp049;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.scp049.getTarget();
        return !this.scp049.isDrugged() && target != null && target.hasEffect(SCPEffects.PESTILENCE.get()) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.scp049.getTarget();
        return !this.scp049.isDrugged() && target != null && target.hasEffect(SCPEffects.PESTILENCE.get()) && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        this.scp049.playSound(SCPSounds.SCP_049_SPOTTED_TARGET.get());
    }

    @Override
    public void stop() {
        super.stop();
        this.scp049.playSound(SCPSounds.SCP_049_LOSE_TARGET.get());
    }
}
