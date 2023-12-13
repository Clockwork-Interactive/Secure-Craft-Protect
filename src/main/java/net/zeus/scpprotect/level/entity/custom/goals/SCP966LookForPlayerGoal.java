package net.zeus.scpprotect.level.entity.custom.goals;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.event.CommonForgeEvents;
import net.zeus.scpprotect.level.entity.custom.SCP966;

public class SCP966LookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
    private final SCP966 scp966;

    public SCP966LookForPlayerGoal(SCP966 scp966, Class<Player> pTargetType, boolean pMustSee) {
        super(scp966, pTargetType, pMustSee);
        this.scp966 = scp966;
    }

    @Override
    public boolean canUse() {
        Player pendingTarget = this.scp966.level().getNearestPlayer(TargetingConditions.forCombat().range(this.getFollowDistance()).selector(
                (livingEntity -> !(livingEntity instanceof Player player) || !player.isCreative())), this.scp966);

        if (pendingTarget != null && CommonForgeEvents.SCP_966_INSOMNIA.getOrDefault(pendingTarget, 0) > CommonForgeEvents.SCP_966Max) {
            this.target = pendingTarget;
            return true;
        }

        return false;
    }
}
