package net.zeus.scpprotect.level.worldgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.zeus.scpprotect.SCP;

public class SCPStructureTypes {

    public static StructureType<JigsawStructure> SCP_106_STRUCTURE = register("scp_106", JigsawStructure.CODEC);

    private static <S extends Structure> StructureType<S> register(String pName, Codec<S> pCodec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, new ResourceLocation(SCP.MOD_ID, pName), () -> {
            return pCodec;
        });
    }

}
