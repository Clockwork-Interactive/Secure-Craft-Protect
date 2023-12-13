package net.zeus.scpprotect.level.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.block.ModBlockEntities;

import java.util.HashMap;

public class SCP330BlockEntity extends BaseEntityBlock {

    public HashMap<Player, Integer> candiesTaken = new HashMap<>();

    public SCP330BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SCP_330_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {

    }
}
