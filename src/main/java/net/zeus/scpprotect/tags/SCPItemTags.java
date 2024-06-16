package net.zeus.scpprotect.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.zeus.scpprotect.SCP;

public class SCPItemTags {
    public static final TagKey<Item> KEYCARDS = createTag("keycards");
    public static final TagKey<Item> CONCRETE = createTag("concrete");
    public static final TagKey<Item> MODULES = createTag("modules");

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(new ResourceLocation(SCP.MOD_ID, name));
    }
}
