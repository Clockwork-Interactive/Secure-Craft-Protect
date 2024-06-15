package net.zeus.scpprotect.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.datagen.advancements.SeenTrigger;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementProvider extends ForgeAdvancementProvider {
    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new AdvancementGen()));
    }

    public static class AdvancementGen implements ForgeAdvancementProvider.AdvancementGenerator {
        protected Advancement buildAdvancement(Item icon, String title, String description, String id, FrameType frameType, CriterionTriggerInstance criterion, Advancement parent, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(icon, Component.literal(title), Component.literal(description), null, frameType, true, true, false)
                    .addCriterion(id, criterion)
                    .save(saver, new ResourceLocation(SCP.MOD_ID, id), existingFileHelper);
        }

        protected static ImpossibleTrigger.TriggerInstance empty() {
            return new ImpossibleTrigger.TriggerInstance();
        }

        protected static ConsumeItemTrigger.TriggerInstance usedItem(RegistryObject<Item> item) {
            return ConsumeItemTrigger.TriggerInstance.usedItem(item.get());
        }

        protected static RecipeCraftedTrigger.TriggerInstance craftedItem(RegistryObject<Item> item) {
            return RecipeCraftedTrigger.TriggerInstance.craftedItem(item.getId());
        }

        protected static <E extends Entity> SeenTrigger.TriggerInstance seenEntity(RegistryObject<EntityType<E>> entity) {
            return SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(entity.get()));
        }

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement BASE = Advancement.Builder.advancement()
                    .display(
                            SCPItems.REALITY_SCANNER.get(),
                            Component.literal("Secure, Contain, Protect"),
                            Component.literal("Welcome, to the world of SCP"),
                            new ResourceLocation(SCP.MOD_ID,"textures/block/decay_block.png"),
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("secure_contain_protect", RecipeCraftedTrigger.TriggerInstance.craftedItem(SCPItems.REALITY_SCANNER.getId()))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "secure_contain_protect"), existingFileHelper);

            Advancement SAFE_ADVANCEMENT = buildAdvancement(SCPItems.SAFE_ACHIEVEMENT.get(), "Its Not Dangerous, Right?", "Locate a Safe class SCP", "its_not_dangerous_right", FrameType.TASK, empty(), BASE, saver, existingFileHelper);
            Advancement A_TASTE_OF_IMMORTALITY = buildAdvancement(SCPItems.SCP_500_BOTTLE.get(), "A Taste of Immortality", "Use a SCP-500 Pill", "a_taste_of_immortality", FrameType.GOAL, usedItem(SCPItems.SCP_500), SAFE_ADVANCEMENT, saver, existingFileHelper);
            Advancement POTENTIAL_BIOWEAPON = buildAdvancement(SCPItems.SCP_1025.get(), "Potential Bioweapon", "Read a page from SCP-1025", "potential_bioweapon", FrameType.GOAL, empty(), SAFE_ADVANCEMENT, saver, existingFileHelper);

            Advancement EUCLID_ADVANCEMENT = buildAdvancement(SCPItems.EUCLID_ACHIEVEMENT.get(), "Controlled Chaos", "Locate a Euclid class SCP", "controlled_chaos", FrameType.TASK, empty(), BASE, saver, existingFileHelper);
            Advancement DOCTOR_DOCTOR = buildAdvancement(SCPItems.LAVENDER.get(), "Doctor, Doctor", "Encounter SCP-049", "doctor_doctor", FrameType.GOAL, seenEntity(SCPEntities.SCP_049), EUCLID_ADVANCEMENT, saver, existingFileHelper);
            Advancement GOOD_HEAVENS = buildAdvancement(Items.POTION.getDefaultInstance().getItem(), "Good Heavens", "Catch the mysterious Pestilence Effect", "good_heavens", FrameType.GOAL, empty(), DOCTOR_DOCTOR, saver, existingFileHelper);
            Advancement DONT_LOOK_AT_ME = buildAdvancement(SCPItems.POLAROID.get(), "Don't Look at Me", "Encounter SCP-096", "dont_look_at_me", FrameType.GOAL, seenEntity(SCPEntities.SCP_096), EUCLID_ADVANCEMENT, saver, existingFileHelper);
            Advancement ITS_JUST_A_STATUE = buildAdvancement(Blocks.YELLOW_CONCRETE.asItem(), "It's Just a Statue...", "Encounter SCP-173", "its_just_a_statue", FrameType.GOAL, seenEntity(SCPEntities.SCP_173), EUCLID_ADVANCEMENT, saver, existingFileHelper);
            Advancement IT_MOVES = buildAdvancement(Blocks.YELLOW_CONCRETE_POWDER.asItem(), "It Moves", "Blink around SCP-173", "it_moves", FrameType.GOAL, empty(), ITS_JUST_A_STATUE, saver, existingFileHelper);
            Advancement RAPID_EYE_MOVEMENT = buildAdvancement(Blocks.GRAY_BED.asItem(), "Rapid Eye Movement", "Encounter SCP-966", "rapid_eye_movement", FrameType.GOAL, seenEntity(SCPEntities.SCP_966), EUCLID_ADVANCEMENT, saver, existingFileHelper);
            Advancement BRAVO_SIX_GOING_DARK = buildAdvancement(SCPItems.NODS.get(), "Bravo Six, Going Dark", "Craft Night Vision Goggles", "bravo_six_going_dark", FrameType.TASK, craftedItem(SCPItems.NODS), RAPID_EYE_MOVEMENT, saver, existingFileHelper);

            Advancement KETER_ADVANCEMENT = buildAdvancement(SCPItems.KETER_ACHIEVEMENT.get(), "Dead Men Tell No Tales", "Locate a Keter class SCP", "dead_men_tell_no_tales", FrameType.TASK, empty(), BASE, saver, existingFileHelper);
            Advancement A_DECAYED_MARCH = buildAdvancement(SCPBlocks.DECAY_BLOCK.get().asItem(), "A Decayed March", "Encounter SCP-106", "a_decayed_march", FrameType.GOAL, seenEntity(SCPEntities.SCP_106), KETER_ADVANCEMENT, saver, existingFileHelper);
            Advancement NO_MANS_LAND = buildAdvancement(SCPBlocks.CORRODED_TILES.get().asItem(), "No Mans Land", "Escape the Pocket Dimension", "no_mans_land", FrameType.CHALLENGE, empty(), A_DECAYED_MARCH, saver, existingFileHelper);
            Advancement SHOW_YOURSELF = buildAdvancement(SCPItems.ODD_CLAW.get(), "Show Yourself", "Encounter SCP-939", "show_yourself", FrameType.GOAL, seenEntity(SCPEntities.SCP_939), KETER_ADVANCEMENT, saver, existingFileHelper);

            Advancement NINE_TAILED_FOX = buildAdvancement(SCPItems.CONTAINMENT_CAGE.get(), "Nine Tailed Fox", "Contain an SCP with the Containment Cage", "nine_tailed_fox", FrameType.TASK, empty(), BASE, saver, existingFileHelper);
        }
    }
}
