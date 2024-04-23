package net.zeus.scpprotect.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
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

    public static final RegistryObject<Block> EZ_CONCRETE_STAIRS = registerBlock("entrance_zone_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.EZ_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_SLAB = registerBlock("entrance_zone_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
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

    public static final RegistryObject<Block> FLOOR_CONCRETE_STAIRS = registerBlock("floor_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.FLOOR_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> FLOOR_CONCRETE_SLAB = registerBlock("floor_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE = registerBlock("light_containment_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_STAIRS = registerBlock("light_containment_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.LC_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_SLAB = registerBlock("light_containment_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
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

    public static final RegistryObject<Block> MOSAIC_TILE_STAIRS = registerBlock("mosaic_tile_stairs",
            () -> new StairBlock(() -> FacilityBlocks.MOSAIC_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    public static final RegistryObject<Block> MOSAIC_TILE_SLAB = registerBlock("mosaic_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    public static final RegistryObject<Block> WHITE_TILES = registerBlock("white_tiles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    public static final RegistryObject<Block> WHITE_TILES_STAIRS = registerBlock("white_tile_stairs",
            () -> new StairBlock(() -> FacilityBlocks.WHITE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    public static final RegistryObject<Block> WHITE_TILES_SLAB = registerBlock("white_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.0F)));

    // Heavy Containment Zone

    public static final RegistryObject<Block> METAL_PLATE = registerBlock("metal_plate",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> METAL_PLATE_STAIRS = registerBlock("metal_plate_stairs",
            () -> new StairBlock(() -> FacilityBlocks.METAL_PLATE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METAL_PLATE_SLAB = registerBlock("metal_plate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METALLIC_PANELS = registerBlock("metallic_panels",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> METALLIC_PANEL_STAIRS = registerBlock("metallic_panel_stairs",
            () -> new StairBlock(() -> FacilityBlocks.METALLIC_PANELS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    public static final RegistryObject<Block> METALLIC_PANEL_SLAB = registerBlock("metallic_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE = registerBlock("reinforced_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(1.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE_STAIRS = registerBlock("reinforced_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.REINFORCED_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE_SLAB = registerBlock("reinforced_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F)));

    // Containers

    public static final RegistryObject<Block> METAL_CONTAINER = registerBlock("metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED = registerBlock("lined_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> METAL_CONTAINER_GRIDDED = registerBlock("gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED_GRIDDED = registerBlock("lined_gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(1.8F).sound(SoundType.COPPER)));

    // Misc???

    public static final RegistryObject<Block> CEMENT_BRICKS = registerBlock("cement_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(2.0F)));

    public static final RegistryObject<Block> CEMENT_BRICK_STAIRS = registerBlock("cement_brick_stairs",
            () -> new StairBlock(() -> FacilityBlocks.CEMENT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(2.0F)));

    public static final RegistryObject<Block> CEMENT_BRICK_SLAB = registerBlock("cement_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(2.0F)));


    public static final RegistryObject<Block> REINFORCED_METAL = registerBlock("reinforced_metal",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .strength(2.0F)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        SCPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
