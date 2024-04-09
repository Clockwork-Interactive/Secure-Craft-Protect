package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class SoundProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    protected SoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, SCP.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        for (RegistryObject<SoundEvent> event : SCPSounds.SOUND_EVENTS.getEntries()) {
            SoundDefinition definition = SoundDefinition.definition();
            SoundEvent soundEvent = event.get();
            String sound = soundEvent.getLocation().getPath();
            addDefinition(sound, definition);
            add(event, definition);
        }
    }

    private void addDefinition(String name, SoundDefinition definition) {
        definition.with(sound(new ResourceLocation(SCP.MOD_ID, name)));
    }
}
