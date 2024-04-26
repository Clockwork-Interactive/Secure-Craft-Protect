package net.zeus.scpprotect.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.level.block.Block.box;

public class SCPBlockShapes {

    public static final VoxelShape BLOCK = box(0, 0, 0, 16, 16, 16);

    public static final VoxelShape FLUORESCENT_LIGHT = Block.box(1, 0, 1, 15, 3, 15);

    public static final VoxelShape OFFICE_LAMP = Shapes.join(Block.box(6, 7, 6, 10, 11, 10), Block.box(4, 5, 4, 12, 7, 12), BooleanOp.OR);
}
