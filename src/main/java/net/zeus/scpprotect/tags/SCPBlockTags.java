package net.zeus.scpprotect.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.zeus.scpprotect.SCP;

public class SCPBlockTags {

    private static TagKey<Block> createTag(String name) {
        return BlockTags.create(new ResourceLocation(SCP.MOD_ID, name));
    }
}
