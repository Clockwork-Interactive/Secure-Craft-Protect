package net.zeus.scpprotect.level.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.custom.SCP019;
import net.zeus.scpprotect.level.block.custom.SCP310;
import net.zeus.scpprotect.level.block.custom.SCP330;
import net.zeus.scpprotect.level.block.custom.SculptureExcrement;
import net.zeus.scpprotect.level.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SCP.MOD_ID);

    public static final RegistryObject<Block> SCULPTURE_EXCREMENT = registerBlock("sculpture_excrements",
            () -> new SculptureExcrement(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT)
                    .strength(2.0F).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> AGED_BRICKS = registerBlock("aged_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> AGED_CONCRETE = registerBlock("aged_concrete",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> SCP_019 = registerBlock("scp_019",
            () -> new SCP019(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> SCP_330 = registerBlock("scp_330",
            () -> new SCP330(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(2.0F).noOcclusion().noLootTable()));

    public static final RegistryObject<Block> SCP_310 = registerBlock("scp_310",
            () -> new SCP310(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL)
                    .strength(2.0F).noOcclusion().noLootTable().lightLevel((pState) -> 15)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
