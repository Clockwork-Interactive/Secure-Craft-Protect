package net.zeus.scpprotect.level.worldgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.zeus.scpprotect.SCP;

public class ModStructures {

    public static ResourceKey<Structure> SCP_106_STRUCTURE = createKey("scp_106");


    public static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(SCP.MOD_ID, name));
    }

}
