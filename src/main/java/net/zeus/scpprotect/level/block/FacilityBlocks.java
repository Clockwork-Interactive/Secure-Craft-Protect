package net.zeus.scpprotect.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.function.Supplier;

public class FacilityBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SCP.MOD_ID);

    // Entrance Zone

    public static final RegistryObject<Block> EZ_CONCRETE = registerBlock("entrance_zone_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_BORDER = registerBlock("entrance_zone_concrete_border",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.ORANGE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_STRIPED = registerBlock("entrance_zone_concrete_striped",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    // Light Containment Zone

    public static final RegistryObject<Block> FLOOR_CONCRETE = registerBlock("floor_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK)
                    .strength(1.8F)));


    public static final RegistryObject<Block> LC_CONCRETE = registerBlock("light_containment_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_BLACK_BORDER = registerBlock("light_containment_concrete_black_border",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_METAL_BORDER_BOTTOM = registerBlock("light_containment_concrete_ridged_bottom",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_METAL_BORDER_TOP = registerBlock("light_containment_concrete_ridged_top",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> MOSAIC_TILES = registerBlock("mosaic_tiles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    public static final RegistryObject<Block> WHITE_TILES = registerBlock("white_tiles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    // Heavy Containment Zone

    public static final RegistryObject<Block> METAL_PLATE = registerBlock("metal_plate",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METALLIC_PANELS = registerBlock("metallic_panels",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE = registerBlock("reinforced_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    // Containers

    public static final RegistryObject<Block> METAL_CONTAINER = registerBlock("metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED = registerBlock("lined_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METAL_CONTAINER_GRIDDED = registerBlock("gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED_GRIDDED = registerBlock("lined_gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        SCPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
