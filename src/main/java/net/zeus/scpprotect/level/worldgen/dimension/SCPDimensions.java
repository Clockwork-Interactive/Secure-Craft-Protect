package net.zeus.scpprotect.level.worldgen.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.zeus.scpprotect.SCP;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class SCPDimensions {

    public static final ResourceKey<LevelStem> SCP_106 = registerKey("scp_106");
    public static final ResourceKey<Level> SCP_106_LEVEL = registerLevelKey("scp_106");
    public static final ResourceKey<DimensionType> SCP_106_TYPE = registerTypeKey("scp_106_type");

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(SCP_106_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                true,
                true,
                1.0D,
                false,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0F,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> type = context.lookup(Registries.DIMENSION_TYPE);

        FlatLevelSource source = new FlatLevelSource(new FlatLevelGeneratorSettings(Optional.empty(), biome.getOrThrow(Biomes.PLAINS), List.of()));

        LevelStem stem = new LevelStem(type.getOrThrow(SCP_106_TYPE), source);

        context.register(SCP_106, stem);
    }

    private static ResourceKey<LevelStem> registerKey(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(SCP.MOD_ID, name));
    }

    private static ResourceKey<Level> registerLevelKey(String name) {
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(SCP.MOD_ID, name));
    }

    private static ResourceKey<DimensionType> registerTypeKey(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(SCP.MOD_ID, name));
    }

}
