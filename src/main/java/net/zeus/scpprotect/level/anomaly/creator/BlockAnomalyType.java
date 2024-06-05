package net.zeus.scpprotect.level.anomaly.creator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.entity.ContainmentBlockEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;

import java.util.function.Supplier;

public class BlockAnomalyType<E extends Block> extends AnomalyType<Supplier<Block>, E> {

    public BlockAnomalyType(Supplier<Block> type, SCP.SCPTypes scptypes) {
        super(type, scptypes);
    }

    @Override
    public Supplier<Block> createRaw(Level level, Vec3 pos) {
        level.setBlock(BlockPos.containing(pos), this.type.get().defaultBlockState(), 3);
        return this.type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends E> R create(Level level, Vec3 pos) {
        level.setBlock(BlockPos.containing(pos), this.type.get().defaultBlockState(), 3);
        return (R) this.type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends E> R create(Level level) {
        return (R) this.type;
    }

    @Override
    public <R extends E> R createContained(Level level, Vec3 pos) {
        BlockPos spawn = BlockPos.containing(pos);
        level.setBlock(spawn, SCPBlocks.CONTAINMENT_BLOCK.get().defaultBlockState(), 3);
        BlockEntity entity = level.getBlockEntity(spawn);
        if (entity instanceof ContainmentBlockEntity containmentBlockEntity) {
            containmentBlockEntity.setAnomalyRegistry(this);
        }
        return (R) level.getBlockState(spawn).getBlock();
    }

    @Override
    public ItemStack getItemized() {
        return this.type.get().asItem().getDefaultInstance();
    }

}
