package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultiDecorBlock extends FaceAttachedHorizontalDirectionalBlock {
    protected VoxelShape floorShape;
    protected VoxelShape ceilingShape;
    protected VoxelShape wallShapeE;
    protected VoxelShape wallShapeW;
    protected VoxelShape wallShapeN;
    protected VoxelShape wallShapeS;

    public MultiDecorBlock(Properties pProperties, VoxelShape floorShape, VoxelShape ceilingShape, VoxelShape wallShapeE, VoxelShape wallShapeW, VoxelShape wallShapeN, VoxelShape wallShapeS) {
        super(pProperties);
        this.floorShape = floorShape;
        this.ceilingShape = ceilingShape;
        this.wallShapeE = wallShapeE;
        this.wallShapeW = wallShapeW;
        this.wallShapeN = wallShapeN;
        this.wallShapeS = wallShapeS;
        this.registerDefaultState(this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACE)) {
            case FLOOR -> this.floorShape;
            case WALL -> switch (pState.getValue(FACING)) {
                case EAST -> this.wallShapeE;
                case WEST -> this.wallShapeW;
                case NORTH -> this.wallShapeN;
                case SOUTH -> this.wallShapeS;
                case DOWN, UP -> null;
            };
            case CEILING -> this.ceilingShape;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACE, FACING);
    }
}
