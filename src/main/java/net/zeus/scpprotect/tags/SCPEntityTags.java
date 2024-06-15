package net.zeus.scpprotect.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.zeus.scpprotect.SCP;

public class SCPEntityTags {

    private static TagKey<EntityType<?>> createTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(SCP.MOD_ID, name));
    }
}
