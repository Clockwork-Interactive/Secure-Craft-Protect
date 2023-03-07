package net.zeus.scppancakes.entity.custom.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;
import net.zeus.scppancakes.entity.custom.SCP173i;

public class SCP173AttackGoal extends MeleeAttackGoal {
    private final SCP173i scp173;

    public SCP173AttackGoal(SCP173i scp173i, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(scp173i, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.scp173 = scp173i;
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
                this.checkAndPerformAttack(livingentity, this.scp173.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity));
            }
        }
    }

}
