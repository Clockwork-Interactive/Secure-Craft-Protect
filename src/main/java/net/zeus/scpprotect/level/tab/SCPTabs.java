package net.zeus.scpprotect.level.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.block.fluid.SCP006Block;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.items.InstantDeleteItem;
import net.zeus.scpprotect.level.item.items.PinkCandy;
import net.zeus.scpprotect.level.item.items.SolidBucketMobItem;

public class SCPTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SCP.MOD_ID);


    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = TABS.register("block_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.blocks")).icon(() ->
                    new ItemStack(SCPBlocks.SCP_019.get())).displayItems((enabledFeatures, entries) -> {
                for (RegistryObject<Block> key : SCPBlocks.BLOCKS.getEntries()) {
                    if (key.get() instanceof FlowerBlock) continue;
                    entries.accept(key.get());
                }
                for (RegistryObject<Block> key : FacilityBlocks.BLOCKS.getEntries()) {
                    if (key.get() instanceof LiquidBlock) continue;
                    entries.accept(key.get());
                }
            }).build());

    public static final RegistryObject<CreativeModeTab> ENTITY_TAB = TABS.register("entity_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.entities")).icon(() ->
                    new ItemStack(SCPItems.SCP_173_SPAWN_EGG.get())).displayItems((enabledFeatures, entries) -> {
                for (RegistryObject<Item> key : SCPItems.ITEMS.getEntries()) {
                    if (key.get() instanceof ForgeSpawnEggItem && !(key.get() == SCPItems.REBEL_SPAWN_EGG.get())) {
                        entries.accept(key.get());
                    }
                }

            }).build());


    public static final RegistryObject<CreativeModeTab> ITEM_TAB = TABS.register("item_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.items")).icon(() -> new ItemStack(SCPItems.SCP_1025.get()))
            .displayItems((enabledFeatures, entries) -> {
                for (RegistryObject<Item> key : SCPItems.ITEMS.getEntries()) {
                    if (!(key.get() instanceof InstantDeleteItem || key.get() instanceof ForgeSpawnEggItem)) {
                        if (!(key.get() instanceof BlockItem) || key.equals(SCPItems.LAVENDER) || key.get() instanceof SolidBucketMobItem) {
                            if (key.equals(SCPItems.CANDY_PINK)) continue;
                            entries.accept(key.get());
                        }
                    }
                }
            }).build());

}
