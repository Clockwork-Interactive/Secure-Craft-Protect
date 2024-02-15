package net.zeus.scpprotect.level.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.block.ModBlockEntities;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.entity.entities.SCP019_2;

public class SCP019BlockEntity extends BaseEntityBlock {

    public int tickCount = 0;
    public int spawnTimer = 1200;

    public SCP019BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SCP_019_BE.get(), pPos, pBlockState);
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
            SCP019_2 scp019_2 = new SCP019_2(ModEntity.SCP_019_2.get(), this.level);
            scp019_2.teleportTo(this.getBlockPos().getX(), this.getBlockPos().getY() + 1, this.getBlockPos().getZ());
            this.level.addFreshEntity(scp019_2);
        }

    }

}
