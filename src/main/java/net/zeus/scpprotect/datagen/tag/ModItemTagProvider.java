package net.zeus.scpprotect.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.tags.SCPItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SCP.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(SCPItemTags.KEYCARDS).add(
                SCPItems.LEVEL_1_KEYCARD.get(),
                SCPItems.LEVEL_2_KEYCARD.get(),
                SCPItems.LEVEL_3_KEYCARD.get(),
                SCPItems.LEVEL_4_KEYCARD.get(),
                SCPItems.LEVEL_5_KEYCARD.get(),
                SCPItems.LEVEL_OMNI_KEYCARD.get()
        );
        tag(SCPItemTags.CONCRETE).add(
                Items.WHITE_CONCRETE,
                Items.LIGHT_GRAY_CONCRETE,
                Items.BLACK_CONCRETE,
                Items.BROWN_CONCRETE,
                Items.RED_CONCRETE,
                Items.ORANGE_CONCRETE,
                Items.YELLOW_CONCRETE,
                Items.LIME_CONCRETE,
                Items.GREEN_CONCRETE,
                Items.CYAN_CONCRETE,
                Items.LIGHT_BLUE_CONCRETE,
                Items.BLUE_CONCRETE,
                Items.PURPLE_CONCRETE,
                Items.MAGENTA_CONCRETE,
                Items.PINK_CONCRETE
        );
    }
}
