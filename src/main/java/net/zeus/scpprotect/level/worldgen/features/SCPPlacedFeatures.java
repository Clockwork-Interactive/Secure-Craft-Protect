package net.zeus.scpprotect.level.worldgen.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

import java.util.List;

public class SCPPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registries.PLACED_FEATURE, SCP.MOD_ID);

    public static final RegistryObject<PlacedFeature> LAVENDER = PLACED_FEATURES.register("lavender_placed", () -> new PlacedFeature(SCPConfiguredFeatures.LAVENDER.getHolder().get(),
            List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

}
