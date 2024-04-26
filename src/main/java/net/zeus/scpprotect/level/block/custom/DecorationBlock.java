package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecorationBlock extends Block {
    protected VoxelShape shape;

    public DecorationBlock(BlockBehaviour.Properties property, VoxelShape shape) {
        super(property);
        this.shape = shape;
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return shape;
    }
}
