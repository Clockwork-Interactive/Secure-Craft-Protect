package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.registry.block.BaseHorizontalEntityBlock;
import net.refractionapi.refraction.voxels.VoxelUtil;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPVoxelShapes;
import net.zeus.scpprotect.level.block.entity.SCP310BlockEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.jetbrains.annotations.Nullable;

public class SCP310 extends BaseHorizontalEntityBlock implements Anomaly {

    public SCP310(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SCPVoxelShapes.SCP_310[VoxelUtil.getDirection(blockState)];
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SCP310BlockEntity(pPos, pState);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_310;
    }
}
