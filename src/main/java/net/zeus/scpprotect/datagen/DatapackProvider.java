package net.zeus.scpprotect.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.worldgen.dimension.SCPDimensions;
import net.zeus.scpprotect.level.worldgen.features.SCPConfiguredFeatures;
import net.zeus.scpprotect.level.worldgen.features.SCPPlacedFeatures;
import net.zeus.scpprotect.level.worldgen.structure.SCPStructures;
import net.zeus.scpprotect.level.worldgen.structure.structures.SCPPools;
import net.zeus.scpprotect.util.SCPDamageTypes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, SCPDamageTypes::bootstrap)
            .add(Registries.DIMENSION_TYPE, SCPDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, SCPDimensions::bootstrapStem)
            .add(Registries.TEMPLATE_POOL, SCPPools::bootstrap)
            .add(Registries.STRUCTURE, SCPStructures::boostrapStructures)
            .add(Registries.CONFIGURED_FEATURE, SCPConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, SCPPlacedFeatures::bootstrap);

    public DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(SCP.MOD_ID));
    }

}
