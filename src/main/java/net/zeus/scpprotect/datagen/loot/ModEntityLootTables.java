package net.zeus.scpprotect.datagen.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {


    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        for (EntityType<?> entityType : this.getKnownEntityTypes().toList()) {
            this.add(entityType, LootTable.lootTable());
        }
        this.add(SCPEntities.SCP_3199.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.CHICKEN).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))).add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))));
        this.add(SCPEntities.SCP_939.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(0.8F)).add(LootItem.lootTableItem(SCPItems.ODD_CLAW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return SCPEntities.ENTITIES.getEntries().stream().filter(obj -> obj.get().getCategory() != MobCategory.MISC).map(RegistryObject::get);
    }
}
