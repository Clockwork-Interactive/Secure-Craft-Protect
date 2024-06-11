package net.zeus.scpprotect.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.level.block.SCPBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(SCPBlocks.SCP_019);
        dropSelf(SCPBlocks.SCP_310);
        dropSelf(SCPBlocks.SCP_330);
        dropSelf(SCPBlocks.LAVENDER);

        for (Block registry : this.getKnownBlocks()) {
            dropSelf(registry);
        }

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SCPBlocks.BLOCKS.getEntries().stream().filter((r) -> r.get().getLootTable() != BuiltInLootTables.EMPTY).map(RegistryObject::get)::iterator;
    }

    private void add(RegistryObject<Block> block, LootItemCondition.Builder builder) {
        add(block, builder);
    }

    private void dropSelf(RegistryObject<Block> block) {
        dropSelf(block.get());
    }
}
