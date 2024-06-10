package net.zeus.scpprotect.datagen.loot;

import com.sun.jna.Function;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.level.block.FacilityBlocks;
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

//        for (RegistryObject<Block> registry : FacilityBlocks.BLOCKS.getEntries()) {
//
//        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SCPBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void add(RegistryObject<Block> block, LootItemCondition.Builder builder) {
        add(block, builder);
    }

    private void dropSelf(RegistryObject<Block> block) {
        dropSelf(block.get());
    }
}
