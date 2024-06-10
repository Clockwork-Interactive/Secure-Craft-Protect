package net.zeus.scpprotect.level.entity.goals.node;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.zeus.scpprotect.level.block.blocks.FacilityDoorBlock;
import net.zeus.scpprotect.util.Misc;

public class SCP096NodeEvaluator extends WalkNodeEvaluator {

    @Override
    protected BlockPathTypes evaluateBlockPathType(BlockGetter pLevel, BlockPos pPos, BlockPathTypes pPathTypes) {
        pPathTypes = super.evaluateBlockPathType(pLevel, pPos, pPathTypes);
        if (Misc.isDoor(pLevel, pPos)) {
            pPathTypes = BlockPathTypes.WALKABLE_DOOR;
        }
        return pPathTypes;
    }
}
