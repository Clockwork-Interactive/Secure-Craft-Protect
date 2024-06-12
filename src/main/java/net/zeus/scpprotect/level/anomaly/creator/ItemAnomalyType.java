package net.zeus.scpprotect.level.anomaly.creator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.entity.ContainmentBlockEntity;

import java.util.function.Supplier;

public class ItemAnomalyType<E extends Item> extends AnomalyType<Supplier<E>, ItemStack> {

    public Supplier<ItemStack> itemized;

    public ItemAnomalyType(Supplier<E> type, SCP.SCPTypes scptypes) {
        super(type, scptypes);
    }

    public ItemAnomalyType(Supplier<E> type, Supplier<ItemStack> itemized, SCP.SCPTypes scptypes) {
        super(type, scptypes);
        this.itemized = itemized;
    }

    @Override
    public Supplier<E> createRaw(Level level, Vec3 pos) {
        ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, this.create(level));
        level.addFreshEntity(itemEntity);
        return this.type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ItemStack> R create(Level level, Vec3 pos) {
        return (R) new ItemStack(this.type.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ItemStack> R create(Level level) {
        return (R) new ItemStack(this.type.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ItemStack> R createContained(Level level, Vec3 pos) {
        BlockPos spawn = BlockPos.containing(pos);
        level.setBlock(spawn, SCPBlocks.CONTAINMENT_BLOCK.get().defaultBlockState(), 3);
        BlockEntity entity = level.getBlockEntity(spawn);
        if (entity instanceof ContainmentBlockEntity containmentBlockEntity) {
            containmentBlockEntity.setAnomalyRegistry(this);
        }
        return (R) new ItemStack(this.type.get());
    }

    @Override
    public ItemStack getItemized() {
        return this.itemized != null ? this.itemized.get() : new ItemStack(this.type.get());
    }

}
