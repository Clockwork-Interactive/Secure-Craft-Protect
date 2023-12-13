package net.zeus.scpprotect.level.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.voxels.VoxelUtil;

import java.util.stream.Stream;

public class VoxelShapes {

    public static final VoxelShape[] SCP_019 = VoxelUtil.makeHorizontalShapes(Stream.of(
            Block.box(3, 0, 3, 13, 2, 13),
            Block.box(4, 2, 4, 12, 5, 12),
            Block.box(2, 5, 2, 14, 16, 14),
            Block.box(14, 11, 10, 17, 12, 11),
            Block.box(16, 11, 6, 17, 12, 10),
            Block.box(-1, 11, 6, 0, 12, 10),
            Block.box(14, 11, 5, 17, 12, 6),
            Block.box(-1, 11, 10, 2, 12, 11),
            Block.box(-1, 11, 5, 2, 12, 6),
            Block.box(5, 16, 5, 11, 19, 11),
            Block.box(3, 19, 3, 13, 21, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get());

    public static final VoxelShape SCP_330 = Block.box(3, 0, 3, 13, 4, 13);

    public static final VoxelShape[] SCP_310 = VoxelUtil.makeHorizontalShapes(Stream.of(
            Block.box(5, 0, 5, 11, 1, 11),
            Block.box(5, 1, 11, 11, 2, 12),
            Block.box(5, 1, 4, 11, 2, 5),
            Block.box(11, 1, 5, 12, 2, 11),
            Block.box(4, 1, 5, 5, 2, 11),
            Block.box(7, 1, 7, 9, 4, 9),
            Block.box(8, 4, 7.5, 8, 5, 8.5),
            Block.box(8, 4, 7.5, 8, 5, 8.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get());

}
