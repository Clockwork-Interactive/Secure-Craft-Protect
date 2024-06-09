package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.entity.FacilityButtonBlockEntity;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.misc.SCPTags;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.level.tab.SCPTabs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class FacilityButtonBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty POWERED;
    protected static final VoxelShape CEILING_AABB_X;
    protected static final VoxelShape CEILING_AABB_Z;
    protected static final VoxelShape FLOOR_AABB_X;
    protected static final VoxelShape FLOOR_AABB_Z;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;
    private final boolean needsKeycards;
    private final boolean arrowsCanPress;
    private final HashMap<RegistryObject<Item>, Integer> KEYCARDS = new HashMap<>() {{
        put(SCPItems.LEVEL_1_KEYCARD, 1);
        put(SCPItems.LEVEL_2_KEYCARD, 2);
        put(SCPItems.LEVEL_3_KEYCARD, 3);
        put(SCPItems.LEVEL_4_KEYCARD, 4);
        put(SCPItems.LEVEL_5_KEYCARD, 5);
    }};

    public FacilityButtonBlock(BlockBehaviour.Properties pProperties, boolean needsKeycards, boolean arrowsCanPress) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(FACE, AttachFace.WALL)
        );
        this.needsKeycards = needsKeycards;
        this.arrowsCanPress = arrowsCanPress;
    }

    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction $$4 = pState.getValue(FACING);
        pState.getValue(POWERED);
        switch (pState.getValue(FACE)) {
            case FLOOR:
                if ($$4.getAxis() == Direction.Axis.X) {
                    return FLOOR_AABB_X;
                }

                return FLOOR_AABB_Z;
            case WALL:
                return switch ($$4) {
                    case EAST -> EAST_AABB;
                    case WEST -> WEST_AABB;
                    case SOUTH -> SOUTH_AABB;
                    case NORTH, UP, DOWN -> NORTH_AABB;
                };
            case CEILING:
            default:
                if ($$4.getAxis() == Direction.Axis.X) {
                    return CEILING_AABB_X;
                } else {
                    return CEILING_AABB_Z;
                }
        }
    }

    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockState state;
        ItemStack itemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        FacilityButtonBlockEntity blockEntity = (FacilityButtonBlockEntity) pLevel.getBlockEntity(pPos);
        if (blockEntity == null) return InteractionResult.FAIL;
        if (!blockEntity.locked) {
            pLevel.scheduleTick(pPos, this, 40);
        }
        if (this.needsKeycards || blockEntity.locked) {
            pPlayer.level().playSound(null, pPlayer.blockPosition(), SCPSounds.KEYCARD_READER_USE.get(), pPlayer.getSoundSource(), 1.0F, 1.0F);
        } else {
            pPlayer.level().playSound(null, pPlayer.blockPosition(), SCPSounds.ELECTRONIC_BUTTON_USE.get(), pPlayer.getSoundSource(), 1.0F, 1.0F);
        }

        if (itemStack.is(SCPItems.BOOK_OF_CHANGE.get())) {
            blockEntity.locked = !blockEntity.locked;
            pPlayer.displayClientMessage(Component.literal("State Set To: %s".formatted(blockEntity.locked ? "Locked" : "Unlocked")), true);
            blockEntity.setChanged();
        } else if (pPlayer.getItemInHand(InteractionHand.OFF_HAND).is(SCPItems.BOOK_OF_CHANGE.get()) && this.needsKeycards) {
            blockEntity.keycardLevel = (blockEntity.keycardLevel) % 5 + 1;
            pPlayer.displayClientMessage(Component.literal("Now Requires Level %d Keycards".formatted(blockEntity.keycardLevel)), true);
            blockEntity.setChanged();
        } else if (this.needsKeycards && !blockEntity.locked) {
            int keycardLevel = blockEntity.keycardLevel;
            int currentKeycardLevel = KEYCARDS.entrySet().stream().filter(entry -> itemStack.is(entry.getKey().get())).mapToInt(HashMap.Entry::getValue).findFirst().orElse(-1);
            if (currentKeycardLevel >= keycardLevel || itemStack.is(SCPItems.LEVEL_OMNI_KEYCARD.get())) {
                state = this.press(pState, pLevel, pPos);
                pLevel.gameEvent(pPlayer, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
            } else {
                pPlayer.displayClientMessage(Component.literal("You Need A Level %d Keycard!".formatted(blockEntity.keycardLevel)), true);
            }
        } else if (blockEntity.locked) {
            pPlayer.displayClientMessage(Component.literal("It's Locked."), true);
        } else {
            state = this.press(pState, pLevel, pPos);
            pLevel.gameEvent(pPlayer, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
        }
        return InteractionResult.SUCCESS;
    }

    public BlockState press(BlockState pState, Level pLevel, BlockPos pPos) {
        pState = pState.cycle(POWERED);
        pLevel.setBlock(pPos, pState, 3);
        this.updateNeighbours(pState, pLevel, pPos);
        return pState;
    }

    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            if (pState.getValue(POWERED)) {
                this.updateNeighbours(pState, pLevel, pPos);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, false);
        }
    }

    public int getSignal(BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return pBlockState.getValue(POWERED) ? 15 : 0;
    }

    public int getDirectSignal(BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return pBlockState.getValue(POWERED) && getConnectedDirection(pBlockState) == pSide ? 15 : 0;
    }

    public boolean isSignalSource(@NotNull BlockState pState) {
        return true;
    }

    public void tick(BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getValue(POWERED)) {
            this.checkPressed(pState, pLevel, pPos);
        }
    }

    public void entityInside(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (!pLevel.isClientSide && this.arrowsCanPress && !(Boolean) pState.getValue(POWERED)) {
            this.checkPressed(pState, pLevel, pPos);
        }
    }

    protected void checkPressed(BlockState pState, Level pLevel, BlockPos pPos) {
        AbstractArrow $$3 = this.arrowsCanPress ? pLevel.getEntitiesOfClass(AbstractArrow.class, pState.getShape(pLevel, pPos).bounds().move(pPos)).stream().findFirst().orElse((AbstractArrow) null) : null;
        boolean $$4 = $$3 != null;
        boolean $$5 = pState.getValue(POWERED);
        if ($$4 != $$5) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, $$4), 3);
            this.updateNeighbours(pState, pLevel, pPos);
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
        pBuilder.add(FACING, POWERED, FACE);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
        CEILING_AABB_Z = Block.box(5, 14, 3.5, 11, 16, 12.5);
        CEILING_AABB_X = Block.box(3.5, 14, 5, 12.5, 16, 11);
        FLOOR_AABB_Z = Block.box(5, 0, 3.5, 11, 2, 12.5);
        FLOOR_AABB_X = Block.box(3.5, 0, 5, 12.5, 2, 11);
        NORTH_AABB = Block.box(5, 3.5, 14, 11, 12.5, 16);
        SOUTH_AABB = Block.box(5, 3.5, 0, 11, 12.5, 2);
        WEST_AABB = Block.box(14, 3.5, 5, 16, 12.5, 11);
        EAST_AABB = Block.box(0, 3.5, 5, 2, 12.5, 11);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FacilityButtonBlockEntity(pPos, pState);
    }

}
