package net.zeus.scpprotect.level.entity.goals.node;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.zeus.scpprotect.level.entity.entities.SCP096;

public class SCP096NodeEvaluator extends UniversalDoorNodeEval {

    @Override
    protected BlockPathTypes evaluateBlockPathType(BlockGetter pLevel, BlockPos pPos, BlockPathTypes blockPathTypes) {
        blockPathTypes = super.evaluateBlockPathType(pLevel, pPos, blockPathTypes);
        if (blockPathTypes.equals(BlockPathTypes.BLOCKED) && ((SCP096) this.mob).canDestroy(pPos)) {
            blockPathTypes = BlockPathTypes.WALKABLE;
        }
        return blockPathTypes;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter pLevel, int pX, int pY, int pZ) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(pX, pY, pZ);
        BlockPathTypes blockPathTypes = getBlockPathTypeStatic(pLevel, blockPos);
        if (blockPathTypes.equals(BlockPathTypes.BLOCKED) && ((SCP096) this.mob).canDestroy(blockPos)) {
            blockPathTypes = BlockPathTypes.WALKABLE;
        }
        return blockPathTypes;
    }

}
