package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SCP.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlockWithItem(FacilityBlocks.FILECABINET, true);
        horizontalBlockWithItem(SCPBlocks.SCULPTURE_EXCREMENT, true);
        blockWithItem(SCPBlocks.AGED_BRICKS);
        blockWithItem(SCPBlocks.AGED_CONCRETE);

        blockWithItem(FacilityBlocks.CEMENT_BRICKS);
        blockWithItem(FacilityBlocks.REINFORCED_METAL);

        blockWithItem(FacilityBlocks.DIRTY_METAL);
        blockWithItem(FacilityBlocks.OFFICE_CEILING);

        blockWithItem(FacilityBlocks.EZ_CONCRETE);
        blockWithItem(FacilityBlocks.FLOOR_CONCRETE);
        blockWithItem(FacilityBlocks.LC_CONCRETE);
        blockWithItem(FacilityBlocks.MOSAIC_TILES);
        blockWithItem(FacilityBlocks.WHITE_TILES);
        blockWithItem(FacilityBlocks.METAL_PLATE);
        blockWithItem(FacilityBlocks.METALLIC_PANELS);
        blockWithItem(FacilityBlocks.REINFORCED_CONCRETE);

        blockWithItem(SCPBlocks.CORRODED_TILES);
        blockWithItem(SCPBlocks.DECAY_BLOCK);
        blockWithItem(SCPBlocks.OLD_WALL);

        blockWithItem(FacilityBlocks.METAL_CONTAINER);
        blockWithItem(FacilityBlocks.METAL_CONTAINER_GRIDDED);

        horizontalBlockWithItem(SCPBlocks.SCP_019, false);
        horizontalBlockWithItem(SCPBlocks.SCP_330, false);
        horizontalBlockWithItem(SCPBlocks.SCP_310, false);

        flowerBlock(SCPBlocks.LAVENDER);

        sidedBlock(SCPBlocks.OLD_STAINED_WALL, new ResourceLocation(
                SCP.MOD_ID, "block/old_wall_stained"), new ResourceLocation(
                SCP.MOD_ID,"block/old_wall"), new ResourceLocation(
                SCP.MOD_ID,"block/old_wall"));

        // Ez

        sidedBlock(FacilityBlocks.EZ_CONCRETE_BORDER, new ResourceLocation(
                SCP.MOD_ID, "block/entrance_zone_concrete_border"), new ResourceLocation(
                SCP.MOD_ID,"block/entrance_zone_concrete"), new ResourceLocation(
                SCP.MOD_ID,"block/entrance_zone_concrete"));

        sidedBlock(FacilityBlocks.EZ_CONCRETE_STRIPED, new ResourceLocation(
                SCP.MOD_ID, "block/entrance_zone_concrete_striped"), new ResourceLocation(
                SCP.MOD_ID,"block/entrance_zone_concrete"), new ResourceLocation(
                SCP.MOD_ID,"block/entrance_zone_concrete"));

        sidedBlock(FacilityBlocks.OFFICE_GRATING_CEILING, new ResourceLocation(
                SCP.MOD_ID, "block/office_ceiling"), new ResourceLocation(
                SCP.MOD_ID,"block/office_grating_ceiling"), new ResourceLocation(
                SCP.MOD_ID,"block/office_ceiling"));

        // LC

        sidedBlock(FacilityBlocks.LC_CONCRETE_BLACK_BORDER, new ResourceLocation(
                SCP.MOD_ID, "block/light_containment_concrete_black_border"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"));

        sidedBlock(FacilityBlocks.LC_CONCRETE_METAL_BORDER_BOTTOM, new ResourceLocation(
                SCP.MOD_ID, "block/light_containment_concrete_ridged_bottom"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"));

        sidedBlock(FacilityBlocks.LC_CONCRETE_METAL_BORDER_TOP, new ResourceLocation(
                SCP.MOD_ID, "block/light_containment_concrete_ridged_top"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"), new ResourceLocation(
                SCP.MOD_ID,"block/light_containment_concrete"));

        // Containers

        sidedBlock(FacilityBlocks.METAL_CONTAINER_LINED, new ResourceLocation(
                SCP.MOD_ID, "block/lined_metal_container"), new ResourceLocation(
                SCP.MOD_ID,"block/metal_container"), new ResourceLocation(
                SCP.MOD_ID,"block/metal_container"));

        sidedBlock(FacilityBlocks.METAL_CONTAINER_LINED_GRIDDED, new ResourceLocation(
                SCP.MOD_ID, "block/lined_gridded_metal_container"), new ResourceLocation(
                SCP.MOD_ID,"block/gridded_metal_container"), new ResourceLocation(
                SCP.MOD_ID,"block/gridded_metal_container"));

        stairBlock(SCPBlocks.AGED_BRICK_STAIRS, SCPBlocks.AGED_BRICKS);
        slabBlock(SCPBlocks.AGED_BRICK_SLAB, SCPBlocks.AGED_BRICKS);

        stairBlock(SCPBlocks.AGED_CONCRETE_STAIRS, SCPBlocks.AGED_CONCRETE);
        slabBlock(SCPBlocks.AGED_CONCRETE_SLAB, SCPBlocks.AGED_CONCRETE);

        stairBlock(FacilityBlocks.DIRTY_METAL_STAIRS, FacilityBlocks.DIRTY_METAL);
        slabBlock(FacilityBlocks.DIRTY_METAL_SLAB, FacilityBlocks.DIRTY_METAL);

        stairBlock(FacilityBlocks.CEMENT_BRICK_STAIRS, FacilityBlocks.CEMENT_BRICKS);
        slabBlock(FacilityBlocks.CEMENT_BRICK_SLAB, FacilityBlocks.CEMENT_BRICKS);

        stairBlock(FacilityBlocks.EZ_CONCRETE_STAIRS, FacilityBlocks.EZ_CONCRETE);
        slabBlock(FacilityBlocks.EZ_CONCRETE_SLAB, FacilityBlocks.EZ_CONCRETE);

        stairBlock(FacilityBlocks.FLOOR_CONCRETE_STAIRS, FacilityBlocks.FLOOR_CONCRETE);
        slabBlock(FacilityBlocks.FLOOR_CONCRETE_SLAB, FacilityBlocks.FLOOR_CONCRETE);

        stairBlock(FacilityBlocks.LC_CONCRETE_STAIRS, FacilityBlocks.LC_CONCRETE);
        slabBlock(FacilityBlocks.LC_CONCRETE_SLAB, FacilityBlocks.LC_CONCRETE);

        stairBlock(FacilityBlocks.MOSAIC_TILE_STAIRS, FacilityBlocks.MOSAIC_TILES);
        slabBlock(FacilityBlocks.MOSAIC_TILE_SLAB, FacilityBlocks.MOSAIC_TILES);

        stairBlock(FacilityBlocks.WHITE_TILES_STAIRS, FacilityBlocks.WHITE_TILES);
        slabBlock(FacilityBlocks.WHITE_TILES_SLAB, FacilityBlocks.WHITE_TILES);

        stairBlock(FacilityBlocks.METAL_PLATE_STAIRS, FacilityBlocks.METAL_PLATE);
        slabBlock(FacilityBlocks.METAL_PLATE_SLAB, FacilityBlocks.METAL_PLATE);

        stairBlock(FacilityBlocks.METALLIC_PANEL_STAIRS, FacilityBlocks.METALLIC_PANELS);
        slabBlock(FacilityBlocks.METALLIC_PANEL_SLAB, FacilityBlocks.METALLIC_PANELS);

        stairBlock(FacilityBlocks.REINFORCED_CONCRETE_STAIRS, FacilityBlocks.REINFORCED_CONCRETE);
        slabBlock(FacilityBlocks.REINFORCED_CONCRETE_SLAB, FacilityBlocks.REINFORCED_CONCRETE);
    }

    private void stairBlock(RegistryObject<Block> block, RegistryObject<Block> ogBlock) {
        stairsBlock((StairBlock)block.get(), new ResourceLocation(SCP.MOD_ID, "block/" + ogBlock.getId().getPath()));
        simpleBlockItem(block.get(), new ModelFile.ExistingModelFile(new ResourceLocation(SCP.MOD_ID, "block/" + block.getId().getPath()), models().existingFileHelper));
    }

    private void sidedBlock(RegistryObject<Block> block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        simpleBlockWithItem(block.get(), models().cubeBottomTop(block.getId().getPath(), side, bottom, top));
    }

    private void slabBlock(RegistryObject<Block> block, RegistryObject<Block> ogBlock) {
        slabBlock((SlabBlock)block.get(), new ResourceLocation(SCP.MOD_ID, "block/" + ogBlock.getId().getPath()), new ResourceLocation(SCP.MOD_ID, "block/" + ogBlock.getId().getPath()));
        simpleBlockItem(block.get(), new ModelFile.ExistingModelFile(new ResourceLocation(SCP.MOD_ID, "block/" + block.getId().getPath()), models().existingFileHelper));
    }

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void horizontalBlockWithItem(RegistryObject<Block> blockRegistryObject, boolean hasBlockItem) {
        Block block = blockRegistryObject.get();
        ModelFile.ExistingModelFile model = new ModelFile.ExistingModelFile(new ResourceLocation(SCP.MOD_ID, "block/" + block.getDescriptionId().replace("block.scprotect.", "")), models().existingFileHelper);
        horizontalBlock(block, model);
        if (hasBlockItem) {
            simpleBlockItem(block, model);
        }
    }

    private void flowerBlock(RegistryObject<Block> block) {
        simpleBlock(block.get(), new ConfiguredModel(models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout")));
    }
}
