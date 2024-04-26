package net.zeus.scpprotect.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.custom.*;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.function.Supplier;

public class SCPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SCP.MOD_ID);

    // SCPs
    public static final RegistryObject<Block> SCP_310 = registerBlock("scp_310",
            () -> new SCP310(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL)
                    .strength(2.0F).noOcclusion().lightLevel((pState) -> 15)));

    public static final RegistryObject<Block> SCP_330 = registerBlock("scp_330",
            () -> new SCP330(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion()));

    public static final RegistryObject<Block> SCP_019 = registerBlock("scp_019",
            () -> new SCP019(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion()));


    // Misc
    public static final RegistryObject<Block> SCULPTURE_EXCREMENT = registerBlock("sculpture_excrements",
            () -> new SculptureExcrement(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT)
                    .strength(2.0F).sound(SoundType.SLIME_BLOCK).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> LAVENDER = BLOCKS.register("lavender",
            () -> new FlowerBlock(SCPEffects.PESTILENCE, 10, BlockBehaviour.Properties.copy(Blocks.POPPY)));

    public static final RegistryObject<Block> AGED_BRICKS = registerBlock("aged_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));

    public static final RegistryObject<Block> AGED_CONCRETE = registerBlock("aged_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));

    public static final RegistryObject<Block> AGED_BRICK_STAIRS = registerBlock("aged_brick_stairs",
            () -> new StairBlock(() -> SCPBlocks.AGED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));

    public static final RegistryObject<Block> AGED_CONCRETE_STAIRS = registerBlock("aged_concrete_stairs",
            () -> new StairBlock(() -> SCPBlocks.AGED_CONCRETE.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));

    public static final RegistryObject<Block> AGED_BRICK_SLAB = registerBlock("aged_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));

    public static final RegistryObject<Block> AGED_CONCRETE_SLAB = registerBlock("aged_concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noLootTable()));


    // Pocket Dimension
    public static final RegistryObject<Block> CORRODED_TILES = registerBlock("corroded_tiles",
            () -> new PocketDimensionBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).noLootTable()
                    .strength(1.8F)));

    public static final RegistryObject<Block> DECAY_BLOCK = registerBlock("decay_block",
            () -> new PocketDimensionBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).noLootTable()
                    .strength(1.8F)));

    public static final RegistryObject<Block> OLD_WALL = registerBlock("old_wall",
            () -> new PocketDimensionBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).noLootTable()
                    .strength(1.8F)));

    public static final RegistryObject<Block> OLD_STAINED_WALL = registerBlock("old_wall_stained",
            () -> new PocketDimensionBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).noLootTable()
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
