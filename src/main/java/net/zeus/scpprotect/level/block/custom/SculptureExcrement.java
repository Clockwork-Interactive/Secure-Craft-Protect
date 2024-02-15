package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.registry.block.BaseHorizontalBlock;
import net.zeus.scpprotect.level.entity.entities.SCP173;

import java.util.stream.Stream;

public class SculptureExcrement extends BaseHorizontalBlock {
    public SculptureExcrement(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!(pEntity instanceof SCP173)) {
            if (pEntity.position().y <= pPos.getY() + 0.1F) {
                pEntity.makeStuckInBlock(pState, new Vec3(0.5D, 0.05F, 0.5D));
            }
        }
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(0, 0, 0, 16, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return true;
    }
}
