package net.zeus.scppancakes.entity.custom.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.zeus.scppancakes.entity.custom.SCP096;

import java.util.EnumSet;

public class HurtByTargetGoal096 extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private int timestamp;
    private final Class<?>[] toIgnoreDamage;
    private final SCP096 scp096;

    public HurtByTargetGoal096(SCP096 scp096, Class<?>... pToIgnoreDamage) {
        super(scp096, false);
        this.scp096 = scp096;
        this.toIgnoreDamage = pToIgnoreDamage;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        LivingEntity livingentity = this.scp096.getLastHurtByMob();
        if ((livingentity instanceof Player player && player.isCreative()) || !this.scp096.canTrigger()) return false;
        if (livingentity != null) {
            this.scp096.targetMap.put(livingentity, 0);
            return true;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timestamp = this.mob.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;

        super.start();
    }
}
