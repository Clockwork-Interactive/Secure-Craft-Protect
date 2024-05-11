package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.zeus.scpprotect.level.entity.entities.SCP131;

public class SCP131LookAtGoal extends LookAtPlayerGoal {
    public SCP131LookAtGoal(SCP131 pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance);
    }

    public boolean hasTarget() {
        return this.lookAt != null && this.lookAt.isAlive();
    }

    public void tick() {
        if (this.hasTarget()) {
            this.mob.getLookControl().setLookAt(this.lookAt.getX(), this.lookAt.getEyeHeight() - 0.8F, this.lookAt.getZ());
        }
        ((SCP131) this.mob).setStaring(this.hasTarget());
    }

    @Override
    public boolean canUse() {
        boolean can = super.canUse();
        if (!can) {
            ((SCP131) this.mob).setStaring(false);
        }
        return can;
    }

    @Override
    public boolean canContinueToUse() {
        boolean can = super.canContinueToUse();
        if (!can) {
            ((SCP131) this.mob).setStaring(false);
        }
        return can;
    }

}
