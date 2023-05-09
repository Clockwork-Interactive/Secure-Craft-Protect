package net.zeus.scppancakes.entity.custom.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.zeus.scppancakes.entity.custom.base.SCP173;

public class SCP173AttackGoal extends MeleeAttackGoal {
    private final SCP173 scp173;

    public SCP173AttackGoal(SCP173 scp173, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(scp173, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.scp173 = scp173;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        super.stop();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */

    public void tick() {
        LivingEntity livingentity = this.scp173.getTarget();
        if (livingentity != null) {
            if (this.scp173.canMove && !this.scp173.level.isClientSide) {
                this.scp173.teleportTowards(livingentity);
                this.checkAndPerformAttack(livingentity, this.scp173.distanceToSqr(livingentity));
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity pEnemy, double dist) {
        if (dist <= 3) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(pEnemy);
        }

    }

}
