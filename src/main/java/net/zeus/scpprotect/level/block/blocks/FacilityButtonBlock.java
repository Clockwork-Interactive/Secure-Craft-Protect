package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zeus.scpprotect.level.item.SCPItems;

import javax.annotation.Nullable;

public class FacilityButtonBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final BooleanProperty POWERED;
    protected static final VoxelShape CEILING_AABB_X;
    protected static final VoxelShape CEILING_AABB_Z;
    protected static final VoxelShape FLOOR_AABB_X;
    protected static final VoxelShape FLOOR_AABB_Z;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;
    private final BlockSetType type;
    private boolean tempOpen;
    private final boolean arrowsCanPress;

    public FacilityButtonBlock(BlockBehaviour.Properties pProperties, BlockSetType pType, boolean pTempOpen, boolean pArrowsCanPress) {
        super(pProperties.sound(pType.soundType()));
        this.type = pType;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(POWERED, false)).setValue(FACE, AttachFace.WALL));
        this.tempOpen = pTempOpen;
        this.arrowsCanPress = pArrowsCanPress;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction $$4 = (Direction)pState.getValue(FACING);
        boolean $$5 = (Boolean)pState.getValue(POWERED);
        switch ((AttachFace)pState.getValue(FACE)) {
            case FLOOR:
                if ($$4.getAxis() == Direction.Axis.X) {
                    return FLOOR_AABB_X;
                }

                return FLOOR_AABB_Z;
            case WALL:
                VoxelShape var10000;
                switch ($$4) {
                    case EAST:
                        var10000 = EAST_AABB;
                        break;
                    case WEST:
                        var10000 = WEST_AABB;
                        break;
                    case SOUTH:
                        var10000 = SOUTH_AABB;
                        break;
                    case NORTH:
                    case UP:
                    case DOWN:
                        var10000 = NORTH_AABB;
                        break;
                    default:
                        throw new IncompatibleClassChangeError();
                }

                return var10000;
            case CEILING:
            default:
                if ($$4.getAxis() == Direction.Axis.X) {
                    return CEILING_AABB_X;
                } else {
                    return CEILING_AABB_Z;
                }
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockState $$6;
        $$6 = this.press(pState, pLevel, pPos);
        float $$8 = (Boolean)$$6.getValue(POWERED) ? 0.6F : 0.5F;
        pLevel.playSound((Player)null, pPos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, $$8);
        pLevel.gameEvent(pPlayer, (Boolean)$$6.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
        return InteractionResult.SUCCESS;
    }

    public BlockState press(BlockState pState, Level pLevel, BlockPos pPos) {
        pState = (BlockState)pState.cycle(POWERED);
        pLevel.setBlock(pPos, pState, 3);
        this.updateNeighbours(pState, pLevel, pPos);
        return pState;
    }

    protected void playSound(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos, boolean pHitByArrow) {
        pLevel.playSound(pHitByArrow ? pPlayer : null, pPos, this.getSound(pHitByArrow), SoundSource.BLOCKS);
    }

    protected SoundEvent getSound(boolean pIsOn) {
        return pIsOn ? this.type.buttonClickOn() : this.type.buttonClickOff();
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            if (pState.getValue(POWERED)) {
                this.updateNeighbours(pState, pLevel, pPos);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return (Boolean)pBlockState.getValue(POWERED) ? 15 : 0;
    }

    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return (Boolean)pBlockState.getValue(POWERED) && getConnectedDirection(pBlockState) == pSide ? 15 : 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((Boolean)pState.getValue(POWERED)) {
            this.checkPressed(pState, pLevel, pPos);
        }
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide && this.arrowsCanPress && !(Boolean)pState.getValue(POWERED)) {
            this.checkPressed(pState, pLevel, pPos);
        }
    }

    protected void checkPressed(BlockState pState, Level pLevel, BlockPos pPos) {
        AbstractArrow $$3 = this.arrowsCanPress ? pLevel.getEntitiesOfClass(AbstractArrow.class, pState.getShape(pLevel, pPos).bounds().move(pPos)).stream().findFirst().orElse((AbstractArrow) null) : null;
        boolean $$4 = $$3 != null;
        boolean $$5 = (Boolean)pState.getValue(POWERED);
        if ($$4 != $$5) {
            pLevel.setBlock(pPos, (BlockState)pState.setValue(POWERED, $$4), 3);
            this.updateNeighbours(pState, pLevel, pPos);
            this.playSound((Player)null, pLevel, pPos, $$4);
            pLevel.gameEvent($$3, $$4 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
        }

        if ($$4) {
            pLevel.scheduleTick(new BlockPos(pPos), this, 40);
        }

    }

    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, this);
        pLevel.updateNeighborsAt(pPos.relative(getConnectedDirection(pState).getOpposite()), this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{FACING, POWERED, FACE});
    }

    static {
        POWERED = BlockStateProperties.POWERED;
        CEILING_AABB_Z = Block.box(5, 14, 3, 11, 16, 13);
        CEILING_AABB_X = Block.box(3, 14, 5, 13, 16, 11);
        FLOOR_AABB_Z = Block.box(5, 0, 3, 11, 2, 13);
        FLOOR_AABB_X = Block.box(3, 0, 5, 13, 2, 11);
        NORTH_AABB = Block.box(5, 3, 14, 11, 13, 16);
        SOUTH_AABB = Block.box(5, 3, 0, 11, 13, 2);
        WEST_AABB = Block.box(14, 3, 5, 16, 13, 11);
        EAST_AABB = Block.box(0, 3, 5, 2, 13, 11);

    }
}
