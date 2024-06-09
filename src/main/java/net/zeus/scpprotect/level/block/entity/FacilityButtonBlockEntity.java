package net.zeus.scpprotect.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.block.SCPBlockEntities;

public class FacilityButtonBlockEntity extends BaseEntityBlock {

    public int keycardLevel = 1;
    public boolean locked = false;

    public FacilityButtonBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SCPBlockEntities.FACILITY_BUTTON_BE.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("keycardLevel", this.keycardLevel);
        pTag.putBoolean("locked", this.locked);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.keycardLevel = pTag.getInt("keycardLevel");
        this.locked = pTag.getBoolean("locked");
    }

    @Override
    public void tick() {

    }

}
