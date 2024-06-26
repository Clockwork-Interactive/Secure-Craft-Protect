package net.zeus.scpprotect.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.blocks.*;
import net.zeus.scpprotect.level.block.fluid.SCP006Block;
import net.zeus.scpprotect.level.fluid.SCPFluids;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.function.Supplier;

public class FacilityBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SCP.MOD_ID);

    // Entrance Zone

    public static final RegistryObject<Block> EZ_CONCRETE = registerBlock("entrance_zone_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_STAIRS = registerBlock("entrance_zone_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.EZ_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_SLAB = registerBlock("entrance_zone_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_BORDER = registerBlock("entrance_zone_concrete_border",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> EZ_CONCRETE_STRIPED = registerBlock("entrance_zone_concrete_striped",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> MOSAIC_TILES = registerBlock("mosaic_tiles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> MOSAIC_TILE_STAIRS = registerBlock("mosaic_tile_stairs",
            () -> new StairBlock(() -> FacilityBlocks.MOSAIC_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> MOSAIC_TILE_SLAB = registerBlock("mosaic_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WHITE_TILES = registerBlock("white_tiles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WHITE_TILES_STAIRS = registerBlock("white_tile_stairs",
            () -> new StairBlock(() -> FacilityBlocks.WHITE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WHITE_TILES_SLAB = registerBlock("white_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                    .requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)));

    // Light Containment Zone

    public static final RegistryObject<Block> FLOOR_CONCRETE = registerBlock("floor_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> FLOOR_CONCRETE_STAIRS = registerBlock("floor_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.FLOOR_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> FLOOR_CONCRETE_SLAB = registerBlock("floor_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE = registerBlock("light_containment_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_STAIRS = registerBlock("light_containment_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.LC_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_SLAB = registerBlock("light_containment_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_BLACK_BORDER = registerBlock("light_containment_concrete_black_border",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_METAL_BORDER_BOTTOM = registerBlock("light_containment_concrete_ridged_bottom",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    public static final RegistryObject<Block> LC_CONCRETE_METAL_BORDER_TOP = registerBlock("light_containment_concrete_ridged_top",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F)));

    // Heavy Containment Zone

    public static final RegistryObject<Block> METAL_PLATE = registerBlock("metal_plate",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METAL_PLATE_STAIRS = registerBlock("metal_plate_stairs",
            () -> new StairBlock(() -> FacilityBlocks.METAL_PLATE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METAL_PLATE_SLAB = registerBlock("metal_plate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METALLIC_PANELS = registerBlock("metallic_panels",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METALLIC_PANEL_STAIRS = registerBlock("metallic_panel_stairs",
            () -> new StairBlock(() -> FacilityBlocks.METALLIC_PANELS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METALLIC_PANEL_SLAB = registerBlock("metallic_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE = registerBlock("reinforced_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE_STAIRS = registerBlock("reinforced_concrete_stairs",
            () -> new StairBlock(() -> FacilityBlocks.REINFORCED_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.8F)));

    public static final RegistryObject<Block> REINFORCED_CONCRETE_SLAB = registerBlock("reinforced_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.8F)));

    // Containers

    public static final RegistryObject<Block> METAL_CONTAINER = registerBlock("metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED = registerBlock("lined_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METAL_CONTAINER_GRIDDED = registerBlock("gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> METAL_CONTAINER_LINED_GRIDDED = registerBlock("lined_gridded_metal_container",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    // Misc???

    public static final RegistryObject<Block> DIRTY_METAL = registerBlock("dirty_metal",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DIRTY_METAL_STAIRS = registerBlock("dirty_metal_stairs",
            () -> new StairBlock(() -> FacilityBlocks.DIRTY_METAL.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DIRTY_METAL_SLAB = registerBlock("dirty_metal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DIRTY_METAL_FENCE = registerBlock("dirty_metal_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> GRATE_BLOCK = registerBlock("grate_block",
            () -> new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> FILECABINET = registerBlock("file_cabinet", () ->
            new FileCabinetBlock(BlockBehaviour.Properties.of().strength(1.8F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> OFFICE_CEILING = registerBlock("office_ceiling",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> OFFICE_GRATING_CEILING = registerBlock("office_grating_ceiling",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> CEMENT_BRICKS = registerBlock("cement_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));

    public static final RegistryObject<Block> CEMENT_BRICK_STAIRS = registerBlock("cement_brick_stairs",
            () -> new StairBlock(() -> FacilityBlocks.CEMENT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));

    public static final RegistryObject<Block> CEMENT_BRICK_SLAB = registerBlock("cement_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));

    public static final RegistryObject<Block> REINFORCED_METAL = registerBlock("reinforced_metal",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> OFFICE_LAMP = registerBlock("office_lamp",
            () -> new DecorationBlock(BlockBehaviour.Properties.of().lightLevel(s -> 15)
                    .mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().strength(0.2F), SCPVoxelShapes.OFFICE_LAMP));

    public static final RegistryObject<Block> FLUORESCENT_LAMP_BLOCK = registerBlock("fluorescent_lamp_block",
            () -> new Block(BlockBehaviour.Properties.of().lightLevel(s -> 15)
                    .mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().strength(0.3F).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> FLUORESCENT_LIGHT = registerBlock("fluorescent_light",
            () -> new MultiDecorBlock(BlockBehaviour.Properties.of().lightLevel(s -> 15)
                    .mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().strength(0.3F), SCPVoxelShapes.FLUORESCENT_LIGHT_FLOOR, SCPVoxelShapes.FLUORESCENT_LIGHT_CEILING, SCPVoxelShapes.FLUORESCENT_LIGHT_WALL_EAS, SCPVoxelShapes.FLUORESCENT_LIGHT_WALL_WES, SCPVoxelShapes.FLUORESCENT_LIGHT_WALL_NOR, SCPVoxelShapes.FLUORESCENT_LIGHT_WALL_SOU));

    public static final RegistryObject<Block> CAUTION_HAZARD_BLOCK = registerBlock("caution_hazard_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> CAUTION_TRAFFIC_BLOCK = registerBlock("caution_traffic_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    //public static final RegistryObject<Block> IDENTIFIER = registerBlock("identifier",
    //        () -> new IdentifierBlock(BlockBehaviour.Properties.copy(Blocks.OBSERVER)));

    // Doors

    public static final RegistryObject<Block> EZ_DOOR = registerBlock("entrance_zone_door",
            () -> new FacilityDoorBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(3.0F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY), FacilityDoorTypes.ENTRANCE));

    public static final RegistryObject<Block> LCZ_DOOR = registerBlock("light_containment_door",
            () -> new FacilityDoorBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(3.0F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY), FacilityDoorTypes.LIGHT));

    public static final RegistryObject<Block> HCZ_DOOR = registerBlock("heavy_containment_door",
            () -> new FacilityDoorBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(3.0F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY), FacilityDoorTypes.HEAVY));

    public static final RegistryObject<Block> ELECTRONIC_BUTTON = registerBlock("electronic_button",
            () -> new FacilityButtonBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(0.5F).noCollission().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY), false, true));

    public static final RegistryObject<Block> KEYCARD_READER = registerBlock("keycard_reader",
            () -> new FacilityButtonBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE)
                    .strength(0.5F).noCollission().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY), true, false));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        SCPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
