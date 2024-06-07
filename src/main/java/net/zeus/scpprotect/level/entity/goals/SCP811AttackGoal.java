package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.entity.entities.SCP811;
import net.zeus.scpprotect.level.entity.projectiles.ToxicSpit;
import org.jetbrains.annotations.NotNull;

public class SCP811AttackGoal extends MeleeAttackGoal {
    public SCP811AttackGoal(SCP811 scp811, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(scp811, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity pAttackTarget) {
        double reach = super.getAttackReachSqr(pAttackTarget) * 1.5F;
        if (this.mob.distanceTo(pAttackTarget) > 7.0F && this.mob.hasLineOfSight(pAttackTarget) && this.mob.tickCount % 20 == 0) {
            ToxicSpit spit = new ToxicSpit(SCPEntities.TOXIC_SPIT.get(), this.mob.getX(), this.mob.getY() + 1.5F, this.mob.getZ(), this.mob.level());
            double d0 = pAttackTarget.getX() - this.mob.getX();
            double d1 = pAttackTarget.getY(0.3D) - spit.getY() - 0.2F;
            double d2 = pAttackTarget.getZ() - this.mob.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            spit.shoot(d0, d1 + d3 * (double)0.2F, d2, 2.6F, 0.1F);
            this.mob.level().addFreshEntity(spit);
        }
        return reach * reach;
    }
}
