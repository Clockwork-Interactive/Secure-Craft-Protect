package net.zeus.scpprotect.datagen.loot;

import com.machinezoo.noexception.throwing.ThrowingRunnable;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.blocks.FacilityDoorBlock;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

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
        dropSelf(SCPBlocks.CONTAINMENT_BLOCK);
        dropSelf(SCPBlocks.MAGNETIZED_BLOCK);

        for (RegistryObject<Block> registry : FacilityBlocks.BLOCKS.getEntries()) {
            if (registry.equals(FacilityBlocks.FLUORESCENT_LAMP_BLOCK)) {
                dropWhenSilkTouch(registry.get());
                continue;
            }

//            if (registry.get() instanceof FacilityDoorBlock) {
//                createDoorTable(registry.get());
//                continue;
//            }

            dropSelf(registry);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return new ArrayList<>() {{
            addAll(SCPBlocks.BLOCKS.getEntries().stream().filter((r) -> r.get().getLootTable() != BuiltInLootTables.EMPTY).map(RegistryObject::get).toList());
            addAll(FacilityBlocks.BLOCKS.getEntries().stream().filter((r) -> r.get().getLootTable() != BuiltInLootTables.EMPTY).map(RegistryObject::get).toList());
        }};
    }

    private void add(RegistryObject<Block> block, LootItemCondition.Builder builder) {
        add(block, builder);
    }

    private void dropSelf(RegistryObject<Block> block) {
        dropSelf(block.get());
    }
}
