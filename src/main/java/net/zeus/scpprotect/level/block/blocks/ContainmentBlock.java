package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.registry.block.BaseHorizontalEntityBlock;
import net.zeus.scpprotect.level.block.entity.ContainmentBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ContainmentBlock extends BaseHorizontalEntityBlock {

    public ContainmentBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.block();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ContainmentBlockEntity blockEntity = (ContainmentBlockEntity) pLevel.getBlockEntity(pPos);
        if (blockEntity != null && !pLevel.isClientSide) {
            blockEntity.interact();
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ContainmentBlockEntity(pPos, pState);
    }

}
