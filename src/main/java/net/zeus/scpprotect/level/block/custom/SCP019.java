package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.registry.block.BaseHorizontalEntityBlock;
import net.refractionapi.refraction.voxels.VoxelUtil;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.entity.custom.SCP019BlockEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.jetbrains.annotations.Nullable;

public class SCP019 extends BaseHorizontalEntityBlock implements Anomaly {

    public SCP019(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return VoxelShapes.SCP_019[VoxelUtil.getDirection(pState)];
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SCP019BlockEntity(pPos, pState);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }
}
