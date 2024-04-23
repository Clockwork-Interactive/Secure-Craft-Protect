package net.zeus.scpprotect.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.entity.SCPEntity;
import net.zeus.scpprotect.level.interfaces.DataGenObj;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.items.SolidBucketMobItem;
import net.zeus.scpprotect.util.ModDamageTypes;
import org.apache.commons.lang3.text.WordUtils;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, SCP.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (RegistryObject<EntityType<?>> registry : SCPEntity.ENTITIES.getEntries()) {
            String name = registry.get().toShortString().replace("_", " ");
            if (name.contains(" "))
                add(registry.get(), registry.get().toShortString().replace("_", "-").toUpperCase());
            else
                add(registry.get(), WordUtils.capitalize(name));
        }

        for (RegistryObject<Block> registry : SCPBlocks.BLOCKS.getEntries()) {
            if (registry.get() instanceof DataGenObj obj) {
                String customID = obj.customID();
                if (customID != null) {
                    add(registry.get(), customID);
                    continue;
                }
            }
            if (registry.get() instanceof Anomaly) {
                add(registry.get(), registry.get().getDescriptionId().replace("block.scprotect.", "").replace("_", "-").toUpperCase());
                continue;
            }
            add(registry.get(), WordUtils.capitalize(registry.get().getDescriptionId().replace("block.scprotect.", "").replace("_", " ")));
        }

        for (RegistryObject<Block> registry : FacilityBlocks.BLOCKS.getEntries()) {
            add(registry.get(), WordUtils.capitalize(registry.get().getDescriptionId().replace("block.scprotect.", "").replace("_", " ")));
        }

        for (RegistryObject<Item> registry : SCPItems.ITEMS.getEntries()) {
            if (registry.get() instanceof DataGenObj obj) {
                String customID = obj.customID();
                if (customID != null) {
                    add(registry.get(), customID);
                    continue;
                }
            }

            if (registry == SCPItems.SCP_3199_EGG_BUCKET) {
                add(registry.get(), "SCP-3199 Egg Bucket");
                continue;
            }

            if (registry == SCPItems.REBEL_SPAWN_EGG) {
                add(registry.get(), "Rebel Spawn Egg");
                continue;
            }

            if (registry == SCPItems.SCP_019_2_SPAWN_EGG) {
                add(registry.get(), "SCP-019-2 Spawn Egg");
                continue;
            }

            if (registry.get() instanceof Anomaly) {
                add(registry.get(), registry.get().getDescriptionId().replace("item.scprotect.", "").replace("_", "-").toUpperCase());
            } else if (registry.get() instanceof ForgeSpawnEggItem) {
                add(registry.get(), WordUtils.capitalize(registry.get().getDescriptionId().replace("item.scprotect.", "").replace("_", " ")).replace("Scp", "SCP-").replaceFirst(" ", ""));
            } else if (!(registry.get() instanceof BlockItem && !(registry.get() instanceof SolidBucketMobItem))) {
                add(registry.get(), WordUtils.capitalize(registry.get().getDescriptionId().replace("item.scprotect.", "").replace("_", " ")));
            }
        }

        for (RegistryObject<? extends MobEffect> registry : SCPEffects.MOB_EFFECTS.getEntries()) {
            if (registry.get() instanceof DataGenObj obj) {
                String customID = obj.customID();
                if (customID != null) {
                    add(registry.get(), customID);
                    continue;
                }
            }
            add(registry.get(), WordUtils.capitalize(registry.get().getDescriptionId().replace("effect.scprotect.", "").replace("_", " ")));
        }

        add("sounds.scprotect.scp_096_running", "SCP-096 is running!");
        add("sounds.scprotect.scp_096_idle", "SCP-096 is idle nearby!");
        add("sounds.scprotect.scp_096_triggered", "SCP-096 is triggered nearby!");
        add("sounds.scprotect.scp_096_kill", "SCP-096 has killed someone nearby!");
        add("sounds.scprotect.scp_096_seen", "You've looked at it...");
        add("sounds.scprotect.scp_109_pour", "Someone has poured SCP-109!");

        add("creativemodetab.securecraftprotecttab.blocks", "Secure Craft Protect Blocks");
        add("creativemodetab.securecraftprotecttab.entities", "Secure Craft Protect Entities");
        add("creativemodetab.securecraftprotecttab.items", "Secure Craft Protect Items");

        registerDamageType(ModDamageTypes.SCP939_DAMAGE, "%1$s died to unknown causes...");
    }

    private void registerDamageType(ResourceKey<DamageType> damageType, String deathMessage) {
        this.add(damageType.location().toLanguageKey(), deathMessage);
        this.add("death.attack." + damageType.location().toLanguageKey(), deathMessage);
    }

}
