package net.zeus.scpprotect.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
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
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SCPBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void dropSelf(RegistryObject<Block> block) {
        dropSelf(block.get());
    }
}
