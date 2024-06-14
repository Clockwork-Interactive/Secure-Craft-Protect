package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zeus.scpprotect.configs.SCPServerConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class FacilityDoorBlock extends Block {
    public static final DirectionProperty FACING;
    public static final BooleanProperty OPEN;
    public static final BooleanProperty POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;

    protected static final VoxelShape SOUTH_OPEN_AABB;
    protected static final VoxelShape NORTH_OPEN_AABB;
    protected static final VoxelShape WEST_OPEN_AABB;
    protected static final VoxelShape EAST_OPEN_AABB;
    private final FacilityDoorTypes type;

    public FacilityDoorBlock(Properties pProperties, FacilityDoorTypes pType) {
        super(pProperties.sound(pType.soundType()));
        this.type = pType;
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(POWERED, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public FacilityDoorTypes type() {
        return this.type;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction $$4 = pState.getValue(FACING);
        boolean $$5 = !(Boolean)pState.getValue(OPEN);
        return switch ($$4) {
            default -> $$5 ? EAST_AABB : EAST_OPEN_AABB;
            case SOUTH -> $$5 ? SOUTH_AABB : SOUTH_OPEN_AABB;
            case WEST -> $$5 ? WEST_AABB : WEST_OPEN_AABB;
            case NORTH -> $$5 ? NORTH_AABB : NORTH_OPEN_AABB;
        };
    }

    public @NotNull BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf $$6 = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && $$6 == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != $$6 ? (pState.setValue(FACING, pFacingState.getValue(FACING))).setValue(OPEN, pFacingState.getValue(OPEN)).setValue(POWERED, pFacingState.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return $$6 == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            DoublePlantBlock.preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> pState.getValue(OPEN);
            default -> false;
        };
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos $$1 = pContext.getClickedPos();
        Level $$2 = pContext.getLevel();
        if ($$1.getY() < $$2.getMaxBuildHeight() - 1 && $$2.getBlockState($$1.above()).canBeReplaced(pContext)) {
            boolean $$3 = $$2.hasNeighborSignal($$1) || $$2.hasNeighborSignal($$1.above());
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(POWERED, $$3).setValue(OPEN, $$3).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    public InteractionResult use(@NotNull BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!this.type.canOpenByHand()) {
            return InteractionResult.PASS;
        } else {
            pState = pState.cycle(OPEN);
            pLevel.setBlock(pPos, pState, 10);
            this.playSound(pPlayer, pLevel, pPos, pState.getValue(OPEN));
            pLevel.gameEvent(pPlayer, this.isOpen(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    public boolean isOpen(BlockState pState) {
        return pState.getValue(OPEN);
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean $$6 = pLevel.hasNeighborSignal(pPos) || pLevel.hasNeighborSignal(pPos.relative(pState.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (!this.defaultBlockState().is(pBlock) && $$6 != pState.getValue(POWERED)) {
            if ($$6 != pState.getValue(OPEN)) {
                this.playSound(null, pLevel, pPos, $$6);
                pLevel.gameEvent(null, $$6 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            }

            pLevel.setBlock(pPos, pState.setValue(POWERED, $$6).setValue(OPEN, $$6), 2);
        }

    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos $$3 = pPos.below();
        BlockState $$4 = pLevel.getBlockState($$3);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? $$4.isFaceSturdy(pLevel, $$3, Direction.UP) : $$4.is(this);
    }

    private void playSound(@Nullable Entity pSource, Level pLevel, BlockPos pPos, boolean pIsOpening) {
        pLevel.playSound(pSource, pPos, pIsOpening ? this.type.doorOpen() : this.type.doorClose(), SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING, OPEN, POWERED);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Mob mob) {
        return state.getValue(OPEN) ? BlockPathTypes.DOOR_OPEN : BlockPathTypes.DOOR_WOOD_CLOSED;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OPEN = BlockStateProperties.OPEN;
        POWERED = BlockStateProperties.POWERED;
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

        SOUTH_OPEN_AABB = Block.box(0, 0, 0, 0, 0, 0);
        NORTH_OPEN_AABB = Block.box(0, 0, 0, 0, 0, 0);
        WEST_OPEN_AABB = Block.box(0, 0, 0, 0, 0, 0);
        EAST_OPEN_AABB = Block.box(0, 0, 0, 0, 0, 0);

        SOUTH_AABB = Block.box(0, 0, 6.5, 16, 16, 9.5);
        NORTH_AABB = Block.box(0, 0, 6.5, 16, 16, 9.5);
        WEST_AABB = Block.box(6.5, 0, 0, 9.5, 16, 16);
        EAST_AABB = Block.box(6.5, 0, 0, 9.5, 16, 16);
    }
}
