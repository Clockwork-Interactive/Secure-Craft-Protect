package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class RotatedPillarDecorBlock extends DecorationBlock {
    public static final EnumProperty<Direction.Axis> AXIS;
    protected VoxelShape X_SHAPE;
    protected VoxelShape Y_SHAPE;
    protected VoxelShape Z_SHAPE;

    public RotatedPillarDecorBlock(Properties property, VoxelShape xShape, VoxelShape yShape, VoxelShape zShape, VoxelShape negativeYShape) {
        super(property, xShape);
        this.X_SHAPE = xShape;
        this.Y_SHAPE = yShape;
        this.Z_SHAPE = zShape;
    }

    public RotatedPillarDecorBlock(Properties property, VoxelShape shape) {
        super(property, shape);
        this.X_SHAPE = shape;
        this.Y_SHAPE = shape;
        this.Z_SHAPE = shape;
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return rotatePillar(pState, pRot);
    }

    public static BlockState rotatePillar(BlockState pState, Rotation pRotation) {
        return switch (pRotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (pState.getValue(AXIS)) {
                case X -> pState.setValue(AXIS, Direction.Axis.Z);
                case Z -> pState.setValue(AXIS, Direction.Axis.X);
                default -> pState;
            };
            default -> pState;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis());
    }

    static {
        AXIS = BlockStateProperties.AXIS;
    }
}
