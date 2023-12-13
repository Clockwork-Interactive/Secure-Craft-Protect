package net.zeus.scpprotect.level.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.ModBlocks;
import net.zeus.scpprotect.level.item.ModItems;
import net.zeus.scpprotect.level.item.custom.InstantDeleteItem;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SCP.MOD_ID);


    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = TABS.register("block_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.blocks")).icon(() ->
                    new ItemStack(ModItems.BLOCKS_TAB_ICON.get())).displayItems((enabledFeatures, entries) -> {
                for (RegistryObject<Block> key : ModBlocks.BLOCKS.getEntries()) {
                    entries.accept(key.get());
                }
            }).build());

    public static final RegistryObject<CreativeModeTab> ENTITY_TAB = TABS.register("entity_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.entities")).icon(() ->
                    new ItemStack(ModItems.ENTITIES_TAB_ICON.get())).displayItems((enabledFeatures, entries) -> {

                for (RegistryObject<Item> key : ModItems.ITEMS.getEntries()) {
                    if (key.get() instanceof ForgeSpawnEggItem) {
                        entries.accept(key.get());
                    }
                }

            }).build());


    public static final RegistryObject<CreativeModeTab> ITEM_TAB = TABS.register("item_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.securecraftprotecttab.items")).icon(() ->
                    new ItemStack(ModItems.ITEMS_TAB_ICON.get())).displayItems((enabledFeatures, entries) -> {

                for (RegistryObject<Item> key : ModItems.ITEMS.getEntries()) {
                    if (!(key.get() instanceof BlockItem || key.get() instanceof InstantDeleteItem || key.get() instanceof ForgeSpawnEggItem)) {
                        entries.accept(key.get());
                    }
                }


            }).build());

}
