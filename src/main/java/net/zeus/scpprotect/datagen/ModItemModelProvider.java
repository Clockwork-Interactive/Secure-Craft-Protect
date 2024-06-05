package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.items.SolidBucketMobItem;
import net.zeus.scpprotect.level.item.scp.SCP500Bottle;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SCP.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> registry : SCPItems.ITEMS.getEntries()) {
            if (registry.get() instanceof ForgeSpawnEggItem) {
                spawnEgg(registry);
                continue;
            }

            if (registry.get() instanceof SCP500Bottle) continue;

            if (registry.get() instanceof BlockItem && !(registry.get() instanceof SolidBucketMobItem)) continue;

            try { // I'm lazy 🤓
                simpleItem(registry);
            } catch (Exception e) {
                makePlaceholderModel(registry);
            }
        }
        handHeldItem(SCPItems.HAIRBRUSH);
        blockItemWithTexture(SCPBlocks.SCP_019, new ResourceLocation(SCP.MOD_ID, "item/scp_019"));
        blockItemWithTexture(SCPBlocks.SCP_310, new ResourceLocation(SCP.MOD_ID, "item/scp_310"));
        blockItemWithTexture(SCPBlocks.SCP_330, new ResourceLocation(SCP.MOD_ID, "item/scp_330"));
        blockItemWithTexture(SCPBlocks.LAVENDER, new ResourceLocation(SCP.MOD_ID, "block/lavender"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SCP.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder blockItem(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation(SCP.MOD_ID, "block/" + block.getId().getPath()));
    }

    private ItemModelBuilder makePlaceholderModel(RegistryObject<? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation("minecraft", "item/book"));
    }

    private ItemModelBuilder handHeldItem(RegistryObject<? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(SCP.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder blockItemWithTexture(RegistryObject<Block> block, ResourceLocation texture) {
        return withExistingParent(block.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0", texture);
    }

    private ItemModelBuilder spawnEgg(RegistryObject<? extends Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/template_spawn_egg"));
    }

}
