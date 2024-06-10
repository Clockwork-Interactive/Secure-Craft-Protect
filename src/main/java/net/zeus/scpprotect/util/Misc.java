package net.zeus.scpprotect.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zeus.scpprotect.level.block.blocks.FacilityDoorBlock;

public class Misc {
    public static int TPS = 20;

    public static boolean isDoor(BlockGetter level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof FacilityDoorBlock || state.getBlock() instanceof DoorBlock;
    }

}
