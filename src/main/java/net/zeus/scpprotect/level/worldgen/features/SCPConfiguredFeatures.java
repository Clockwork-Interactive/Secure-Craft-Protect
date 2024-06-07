package net.zeus.scpprotect.level.worldgen.features;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.zeus.scpprotect.level.block.SCPBlocks;

import java.util.List;

public class SCPConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVENDER = FeatureUtils.createKey("scprotect:lavender");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, LAVENDER, Feature.FLOWER, new RandomPatchConfiguration(16, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(2345L,
                        new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                        List.of(SCPBlocks.LAVENDER.get().defaultBlockState()))))));
    }

}
