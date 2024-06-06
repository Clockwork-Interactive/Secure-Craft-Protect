package net.zeus.scpprotect.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementProvider extends ForgeAdvancementProvider {
    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new AdvancementGen()));
    }


    public static class AdvancementGen implements ForgeAdvancementProvider.AdvancementGenerator {

        protected static ImpossibleTrigger.TriggerInstance Empty() {
            return new ImpossibleTrigger.TriggerInstance();
        }

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {

            Advancement BASE = Advancement.Builder.advancement()
                    .display(
                            SCPItems.SCP_999_BUCKET.get(),
                            Component.literal("Secure Contain Protect"),
                            Component.literal("Welcome."),
                            new ResourceLocation(SCP.MOD_ID,"block/decay_block"),
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("secure_contain_protect", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "secure_contain_protect"), existingFileHelper);

            Advancement DOCTOR_DOCTOR = Advancement.Builder.advancement()
                    .display(
                            SCPItems.LAVENDER.get(),
                            Component.literal("Doctor, Doctor"),
                            Component.literal("Encounter SCP-049"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("doctor_doctor", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "doctor_doctor"), existingFileHelper);

            Advancement GOOD_HEAVENS = Advancement.Builder.advancement()
                    .display(
                            SCPItems.LAVENDER.get(),
                            Component.literal("Good Heavens"),
                            Component.literal("Catch the mysterious Pestilence Effect"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("good_heavens", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "good_heavens"), existingFileHelper);


            Advancement DONT_LOOK_AT_ME = Advancement.Builder.advancement()
                    .display(
                            SCPItems.POLAROID.get(),
                            Component.literal("Don't Look At Me"),
                            Component.literal("Encounter SCP-096"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("dont_look_at_me", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "dont_look_at_me"), existingFileHelper);

            Advancement A_DECAYED_MARCH = Advancement.Builder.advancement()
                    .display(
                            SCPBlocks.DECAY_BLOCK.get(),
                            Component.literal("A Decayed March"),
                            Component.literal("Encounter SCP-106"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("a_decayed_march", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "a_decayed_march"), existingFileHelper);

            Advancement A_TASTE_OF_IMMORTALITY = Advancement.Builder.advancement()
                    .display(
                            SCPItems.SCP_500_BOTTLE.get(),
                            Component.literal("A Taste Of Immortality"),
                            Component.literal("Use an SCP-500 pill"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("a_taste_of_immortality", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "a_taste_of_immortality"), existingFileHelper);

            Advancement SHOW_YOURSELF = Advancement.Builder.advancement()
                    .display(
                            SCPItems.ODD_CLAW.get(),
                            Component.literal("Show Yourself"),
                            Component.literal("Encounter SCP-939"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("show_yourself", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "show_yourself"), existingFileHelper);

            Advancement RAPID_EYE_MOVEMENT = Advancement.Builder.advancement()
                    .display(
                            Blocks.GRAY_BED,
                            Component.literal("Rapid Eye Movement"),
                            Component.literal("Encounter SCP-966"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("rapid_eye_movement", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "rapid_eye_movement"), existingFileHelper);

            Advancement POTENTIAL_BIOWEAPON = Advancement.Builder.advancement()
                    .display(
                            SCPItems.SCP_1025.get(),
                            Component.literal("'Potential Bioweapon'"),
                            Component.literal("Read a page from SCP-1025"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("potential_bioweapon", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "potential_bioweapon"), existingFileHelper);

            Advancement NO_MANS_LAND = Advancement.Builder.advancement()
                    .display(
                            SCPBlocks.CORRODED_TILES.get(),
                            Component.literal("No Mans Land"),
                            Component.literal("Escape the Pocket Dimension"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("no_mans_land", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "no_mans_land"), existingFileHelper);

            Advancement NINE_TAILED_FOX = Advancement.Builder.advancement()
                    .display(
                            SCPItems.CONTAINMENT_CAGE.get(),
                            Component.literal("Nine Tailed Fox"),
                            Component.literal("Contain an SCP with the Containment Cage"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("nine_tailed_fox", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "nine_tailed_fox"), existingFileHelper);

            Advancement BRAVO_SIX_GOING_DARK = Advancement.Builder.advancement()
                    .display(
                            SCPItems.NODS.get(),
                            Component.literal("Bravo Six, Going Dark"),
                            Component.literal("Craft the Night Vision Goggles"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            true
                    )
                    .addCriterion("bravo_six_going_dark", RecipeCraftedTrigger.TriggerInstance.craftedItem(SCPItems.NODS.getId()))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "bravo_six_going_dark"), existingFileHelper);
        }

    }
}
