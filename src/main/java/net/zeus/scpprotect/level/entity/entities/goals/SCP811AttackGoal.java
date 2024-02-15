package net.zeus.scpprotect.level.entity.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.entity.entities.SCP811;
import net.zeus.scpprotect.level.entity.entities.projectiles.ToxicSpit;
import org.jetbrains.annotations.NotNull;

public class SCP811AttackGoal extends MeleeAttackGoal {
    public SCP811AttackGoal(SCP811 scp811, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(scp811, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }


    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity pAttackTarget) {
        double ret = super.getAttackReachSqr(pAttackTarget);

        if (this.mob.distanceTo(pAttackTarget) > 7.0F && this.mob.tickCount % 20 == 0) {
            ToxicSpit spit = new ToxicSpit(ModEntity.TOXIC_SPIT.get(), this.mob.getX(), this.mob.getY() + 1.6F, this.mob.getZ(), this.mob.level());
            spit.shootFromRotation(this.mob, this.mob.getXRot(), this.mob.getYRot(), 0.0F, 3.0F, 0.0F);
            this.mob.level().addFreshEntity(spit);
        }

        return ret;
    }
}
