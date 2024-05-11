package net.zeus.scpprotect.level.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.block.SCPBlockEntities;

import java.util.HashMap;

public class SCP330BlockEntity extends BaseEntityBlock {

    public SCP330BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SCPBlockEntities.SCP_330_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {

    }
}
