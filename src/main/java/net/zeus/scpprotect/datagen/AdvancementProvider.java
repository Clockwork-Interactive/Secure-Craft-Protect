package net.zeus.scpprotect.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.datagen.advancements.SeenTrigger;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.effect.SCPPotions;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.item.SCPItems;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.List;
import java.util.Map;
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


            Advancement SAFE_ADVANCEMENT = Advancement.Builder.advancement()
                    .parent(BASE)
                    .display(
                            SCPItems.SAFE_ACHIEVEMENT.get(),
                            Component.literal("Its Not Dangerous, Right?"),
                            Component.literal("Locate a Safe class SCP"),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("its_not_dangerous_right", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "its_not_dangerous_right"), existingFileHelper);

            Advancement EUCLID_ADVANCEMENT = Advancement.Builder.advancement()
                    .parent(BASE)
                    .display(
                            SCPItems.EUCLID_ACHIEVEMENT.get(),
                            Component.literal("Controlled Chaos"),
                            Component.literal("Locate a Euclid class SCP"),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("controlled_chaos", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "controlled_chaos"), existingFileHelper);

            Advancement KETER_ADVANCEMENT = Advancement.Builder.advancement()
                    .parent(BASE)
                    .display(
                            SCPItems.KETER_ACHIEVEMENT.get(),
                            Component.literal("'Dead Men Tell No Tales'"),
                            Component.literal("Locate a Keter class SCP"),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("dead_men_tell_no_tales", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "dead_men_tell_no_tales"), existingFileHelper);

            Advancement DOCTOR_DOCTOR = Advancement.Builder.advancement()
                    .parent(EUCLID_ADVANCEMENT)
                    .display(
                            SCPItems.LAVENDER.get(),
                            Component.literal("Doctor, Doctor"),
                            Component.literal("Encounter SCP-049"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("doctor_doctor", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_049.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "doctor_doctor"), existingFileHelper);

            Advancement GOOD_HEAVENS = Advancement.Builder.advancement()
                    .parent(DOCTOR_DOCTOR)
                    .display(
                            Items.POTION.getDefaultInstance().getItem(),
                            Component.literal("Good Heavens"),
                            Component.literal("Catch the mysterious Pestilence Effect"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("good_heavens", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "good_heavens"), existingFileHelper);


            Advancement DONT_LOOK_AT_ME = Advancement.Builder.advancement()
                    .parent(EUCLID_ADVANCEMENT)
                    .display(
                            SCPItems.POLAROID.get(),
                            Component.literal("Don't Look at Me"),
                            Component.literal("Encounter SCP-096"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("dont_look_at_me", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_096.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "dont_look_at_me"), existingFileHelper);

            Advancement A_DECAYED_MARCH = Advancement.Builder.advancement()
                    .parent(KETER_ADVANCEMENT)
                    .display(
                            SCPBlocks.DECAY_BLOCK.get(),
                            Component.literal("A Decayed March"),
                            Component.literal("Encounter SCP-106"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("a_decayed_march", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_106.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "a_decayed_march"), existingFileHelper);

            Advancement A_TASTE_OF_IMMORTALITY = Advancement.Builder.advancement()
                    .parent(SAFE_ADVANCEMENT)
                    .display(
                            SCPItems.SCP_500_BOTTLE.get(),
                            Component.literal("A Taste of Immortality"),
                            Component.literal("Use an SCP-500 pill"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("a_taste_of_immortality", ConsumeItemTrigger.TriggerInstance.usedItem(SCPItems.SCP_500.get()))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "a_taste_of_immortality"), existingFileHelper);

            Advancement SHOW_YOURSELF = Advancement.Builder.advancement()
                    .parent(KETER_ADVANCEMENT)
                    .display(
                            SCPItems.ODD_CLAW.get(),
                            Component.literal("Show Yourself"),
                            Component.literal("Encounter SCP-939"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("show_yourself", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_939.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "show_yourself"), existingFileHelper);

            Advancement ITS_JUST_A_STATUE = Advancement.Builder.advancement()
                    .parent(EUCLID_ADVANCEMENT)
                    .display(
                            Blocks.YELLOW_CONCRETE,
                            Component.literal("It's Just a Statue..."),
                            Component.literal("Encounter SCP-173"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("its_just_a_statue", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_173.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "its_just_a_statue"), existingFileHelper);

            Advancement IT_MOVES = Advancement.Builder.advancement()
                    .parent(ITS_JUST_A_STATUE)
                    .display(
                            Blocks.YELLOW_CONCRETE_POWDER,
                            Component.literal("It Moves"),
                            Component.literal("Blink around SCP-173"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("it_moves", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "it_moves"), existingFileHelper);

            Advancement RAPID_EYE_MOVEMENT = Advancement.Builder.advancement()
                    .parent(EUCLID_ADVANCEMENT)
                    .display(
                            Blocks.GRAY_BED,
                            Component.literal("Rapid Eye Movement"),
                            Component.literal("Encounter SCP-966"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("rapid_eye_movement", SeenTrigger.TriggerInstance.seen(new EntityPredicate.Builder().of(SCPEntities.SCP_966.get())))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "rapid_eye_movement"), existingFileHelper);

            Advancement POTENTIAL_BIOWEAPON = Advancement.Builder.advancement()
                    .parent(SAFE_ADVANCEMENT)
                    .display(
                            SCPItems.SCP_1025.get(),
                            Component.literal("'Potential Bioweapon'"),
                            Component.literal("Read a page from SCP-1025"),
                            null,
                            FrameType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("potential_bioweapon", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "potential_bioweapon"), existingFileHelper);

            Advancement NO_MANS_LAND = Advancement.Builder.advancement()
                    .parent(A_DECAYED_MARCH)
                    .display(
                            SCPBlocks.CORRODED_TILES.get(),
                            Component.literal("No Mans Land"),
                            Component.literal("Escape the Pocket Dimension"),
                            null,
                            FrameType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("no_mans_land", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "no_mans_land"), existingFileHelper);

            Advancement NINE_TAILED_FOX = Advancement.Builder.advancement()
                    .parent(BASE)
                    .display(
                            SCPItems.CONTAINMENT_CAGE.get(),
                            Component.literal("Nine Tailed Fox"),
                            Component.literal("Contain an SCP with the Containment Cage"),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("nine_tailed_fox", Empty())
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "nine_tailed_fox"), existingFileHelper);

            Advancement BRAVO_SIX_GOING_DARK = Advancement.Builder.advancement()
                    .parent(RAPID_EYE_MOVEMENT)
                    .display(
                            SCPItems.NODS.get(),
                            Component.literal("Bravo Six, Going Dark"),
                            Component.literal("Craft the Night Vision Goggles"),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("bravo_six_going_dark", RecipeCraftedTrigger.TriggerInstance.craftedItem(SCPItems.NODS.getId()))
                    .save(saver, new ResourceLocation(SCP.MOD_ID, "bravo_six_going_dark"), existingFileHelper);
        }

    }
}
