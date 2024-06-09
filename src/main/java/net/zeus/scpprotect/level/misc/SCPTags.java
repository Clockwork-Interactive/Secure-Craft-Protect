package net.zeus.scpprotect.level.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.zeus.scpprotect.SCP;

public class SCPTags {
    public static final TagKey<Item> KEYCARDS = getTag("keycards");

    private static TagKey<Item> getTag(String pName) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SCP.MOD_ID, pName));
    }
}
