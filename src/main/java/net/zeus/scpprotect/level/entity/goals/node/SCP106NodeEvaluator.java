package net.zeus.scpprotect.level.entity.goals.node;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.zeus.scpprotect.level.block.SCPBlocks;

public class SCP106NodeEvaluator extends WalkNodeEvaluator {

    @Override
    protected BlockPathTypes evaluateBlockPathType(BlockGetter pLevel, BlockPos pPos, BlockPathTypes pPathTypes) {
        pPathTypes = super.evaluateBlockPathType(pLevel, pPos, pPathTypes);

        if (pPathTypes == BlockPathTypes.BLOCKED && !pLevel.getBlockState(pPos).is(SCPBlocks.MAGNETIZED_BLOCK.get()) && this.mob.getTarget() != null && pPos.getY() >= this.mob.getY()) {
            LivingEntity target = this.mob.getTarget();
            if (target.getY() > this.mob.getY() + 1.0D) return pPathTypes;
            pPathTypes = BlockPathTypes.WALKABLE;
        }

        return pPathTypes;
    }

}
