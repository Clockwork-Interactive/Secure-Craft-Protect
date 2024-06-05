package net.zeus.scpprotect.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.block.SCPBlockEntities;

public class SCP330BlockEntity extends BaseEntityBlock {

    public SCP330BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SCPBlockEntities.SCP_330_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {

    }
}
