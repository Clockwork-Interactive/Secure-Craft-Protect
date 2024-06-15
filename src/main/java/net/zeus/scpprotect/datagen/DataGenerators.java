package net.zeus.scpprotect.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.datagen.loot.ModLootTableProvider;
import net.zeus.scpprotect.datagen.tag.ModBlockTagProvider;
import net.zeus.scpprotect.datagen.tag.ModFluidTagProvider;
import net.zeus.scpprotect.datagen.tag.ModItemTagProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = SCP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ModBlockTagProvider blockTagProvider = generator.addProvider(true, new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ParticleProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new SoundProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModLanguageProvider(packOutput));
        generator.addProvider(true, new ModFluidTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, new ModItemTagProvider(packOutput, lookupProvider,blockTagProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new DatapackProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, lookupProvider, existingFileHelper));
    }

}
