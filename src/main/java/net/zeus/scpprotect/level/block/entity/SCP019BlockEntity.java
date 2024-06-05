package net.zeus.scpprotect.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.block.SCPBlockEntities;

public class SCP019BlockEntity extends BaseEntityBlock {

    public int tickCount = 0;
    public int spawnTimer = 1200;

    public SCP019BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SCPBlockEntities.SCP_019_BE.get(), pPos, pBlockState);
    }


    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) return;

        this.tickCount++;
        AABB range = new AABB(new BlockPos(this.getBlockPos().getX() - 2, this.getBlockPos().getY() - 2, this.getBlockPos().getZ() - 2), new BlockPos(this.getBlockPos().getX() + 2, this.getBlockPos().getY() + 2, this.getBlockPos().getZ() + 2));
        assert this.level != null;
        boolean hasCold = this.level.getBlockStates(range).filter(blockstate -> blockstate.getBlock() instanceof IceBlock).toArray().length > 0;
        boolean hasWarmth = this.level.getBlockStates(range).filter(blockstate -> blockstate.getBlock() instanceof MagmaBlock || blockstate.getBlock() instanceof FireBlock || blockstate.getFluidState().is(Fluids.FLOWING_LAVA) || blockstate.getFluidState().is(Fluids.LAVA)).toArray().length > 0;

        if (hasCold && hasWarmth) {
            this.spawnTimer = 1200;
        } else if (hasCold) {
            this.spawnTimer = 2400;
        } else if (hasWarmth) {
            this.spawnTimer = 600;
        } else {
            this.spawnTimer = 1200;
        }

        if (this.tickCount % this.spawnTimer == 0) {
            AnomalyRegistry.SCP_019_2.createRaw(this.level, this.getBlockPos().above().getCenter());
        }

    }

}
