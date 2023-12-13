package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SCP.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlockWithItem(ModBlocks.SCULPTURE_EXCREMENT);
        blockWithItem(ModBlocks.AGED_BRICKS);
        blockWithItem(ModBlocks.AGED_CONCRETE);
        horizontalBlockWithItem(ModBlocks.SCP_019);
        horizontalBlockWithItem(ModBlocks.SCP_330);
        horizontalBlockWithItem(ModBlocks.SCP_310);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void horizontalBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        ModelFile.ExistingModelFile model = new ModelFile.ExistingModelFile(new ResourceLocation(SCP.MOD_ID, "block/" + block.getDescriptionId().replace("block.scprotect.", "")), models().existingFileHelper);
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

}
