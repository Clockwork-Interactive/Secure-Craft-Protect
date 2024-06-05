package net.zeus.scpprotect.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.level.anomaly.creator.AnomalyType;
import net.zeus.scpprotect.level.block.SCPBlockEntities;

public class ContainmentBlockEntity extends BaseEntityBlock {

    private AnomalyType<?, ?> anomalyRegistry;

    public ContainmentBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SCPBlockEntities.CONTAINMENT_BE.get(), pPos, pBlockState);
    }

    public void setAnomalyRegistry(AnomalyType<?, ?> anomalyRegistry) {
        this.anomalyRegistry = anomalyRegistry;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.anomalyRegistry != null) {
            pTag.putString("anomalyRegistry", this.anomalyRegistry.getType().toString());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("anomalyRegistry")) {
            this.anomalyRegistry = AnomalyType.getAnomalyType(pTag.getString("anomalyRegistry"));
        }
    }

    public void interact() {
        if (this.anomalyRegistry != null && this.level != null) {
            ItemStack stack = this.anomalyRegistry.getItemized();
            ItemEntity entity = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), stack);
            this.level.addFreshEntity(entity);
        }
        this.level.removeBlock(this.worldPosition, false);
    }

    @Override
    public void tick() {

    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

}
