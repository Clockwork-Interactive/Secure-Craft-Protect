package net.zeus.scpprotect.level.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.fluid.SCPFluids;
import net.zeus.scpprotect.level.item.armor.NightVisionGoggles;
import net.zeus.scpprotect.level.item.items.*;
import net.zeus.scpprotect.level.item.scp.*;

import java.awt.*;
import java.util.function.Supplier;

public class SCPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SCP.MOD_ID);

    // SCP ITEMS
    public static final RegistryObject<Item> SCP_006_BUCKET = ITEMS.register("scp_006_bucket", () -> new SCP006BucketItem(SCPFluids.SOURCE_SCP_006, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCP_500 = ITEMS.register("scp_500", () -> new SCP500Pill(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SCP_1025 = ITEMS.register("scp_1025", () -> new SCP1025(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCP_109 = ITEMS.register("scp_109", () -> new SCP109(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCP_207 = ITEMS.register("scp_207", () -> new SCP207(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CANDY_RED = ITEMS.register("red_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_BLUE = ITEMS.register("blue_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_GREEN = ITEMS.register("green_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_YELLOW = ITEMS.register("yellow_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_ORANGE = ITEMS.register("orange_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_PURPLE = ITEMS.register("purple_candy", () -> new Item(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_PINK = ITEMS.register("pink_candy", () -> new PinkCandy(new Item.Properties().stacksTo(16).food(SCPFoods.CANDY)));
    public static final RegistryObject<Item> BOOK_OF_CHANGE = ITEMS.register("awcy", () -> new BookOfChange(new Item.Properties().stacksTo(1)));

    // SPAWN EGGS
    public static final RegistryObject<Item> SCP_111_SPAWN_EGG = registerEgg("scp_111_spawn_egg", SCPEntities.SCP_111, new Color(189, 38, 38), new Color(246, 237, 181));
    public static final RegistryObject<Item> SCP_131_SPAWN_EGG = registerEgg("scp_131_spawn_egg", SCPEntities.SCP_131, new Color(178, 74, 86), new Color(64, 161, 227));
    public static final RegistryObject<Item> SCP_346_SPAWN_EGG = registerEgg("scp_346_spawn_egg", SCPEntities.SCP_346, new Color(40, 37, 36), new Color(40, 37, 36));
    public static final RegistryObject<Item> SCP_999_SPAWN_EGG = registerEgg("scp_999_spawn_egg", SCPEntities.SCP_999, new Color(254, 115, 10), new Color(254, 166, 44));
    public static final RegistryObject<Item> SCP_049_SPAWN_EGG = registerEgg("scp_049_spawn_egg", SCPEntities.SCP_049, new Color(66, 66, 66), new Color(226, 221, 18));
    public static final RegistryObject<Item> SCP_049_2_SPAWN_EGG = registerEgg("scp_049_2_spawn_egg", SCPEntities.SCP_049_2, new Color(112, 101, 70), new Color(106, 59, 40));
    public static final RegistryObject<Item> SCP_096_SPAWN_EGG = registerEgg("scp_096_spawn_egg", SCPEntities.SCP_096, new Color(245, 233, 229), new Color(237, 212, 197));
    public static final RegistryObject<Item> SCP_173_SPAWN_EGG = registerEgg("scp_173_spawn_egg", SCPEntities.SCP_173, new Color(227, 185, 129), new Color(162, 119, 78, 255));
    public static final RegistryObject<Item> SCP_811_SPAWN_EGG = registerEgg("scp_811_spawn_egg", SCPEntities.SCP_811, new Color(40, 103, 54), new Color(50, 148, 89));
    public static final RegistryObject<Item> SCP_966_SPAWN_EGG = registerEgg("scp_966_spawn_egg", SCPEntities.SCP_966, new Color(193, 163, 163), new Color(232, 180, 180));
    public static final RegistryObject<Item> SCP_019_2_SPAWN_EGG = registerEgg("scp_019_2_spawn_egg", SCPEntities.SCP_019_2, new Color(211, 162, 181), new Color(253, 220, 220));
    public static final RegistryObject<Item> SCP_058_SPAWN_EGG = registerEgg("scp_058_spawn_egg", SCPEntities.SCP_058, new Color(162, 0, 0), new Color(255, 201, 132));
    public static final RegistryObject<Item> SCP_106_SPAWN_EGG = registerEgg("scp_106_spawn_egg", SCPEntities.SCP_106, new Color(81, 53, 54), new Color(11, 0, 4));
    public static final RegistryObject<Item> SCP_939_SPAWN_EGG = registerEgg("scp_939_spawn_egg", SCPEntities.SCP_939, new Color(249, 6, 12), new Color(42, 0, 0));
    public static final RegistryObject<Item> SCP_3199_SPAWN_EGG = registerEgg("scp_3199_spawn_egg", SCPEntities.SCP_3199, new Color(238, 203, 201), new Color(183, 131, 131));
    public static final RegistryObject<Item> REBEL_SPAWN_EGG = registerEgg("rebel_spawn_egg", SCPEntities.REBEL, new Color(231, 190, 132), new Color(208, 129, 74));

    // ARMOR
    public static final RegistryObject<Item> NODS = ITEMS.register("night_vision_goggles", () -> new NightVisionGoggles(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));

    // MISC
    public static final RegistryObject<Item> ODD_CLAW = ITEMS.register("odd_claw", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ODD_CLIMBERS = ITEMS.register("odd_climbers", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HAIRBRUSH = ITEMS.register("hairbrush", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LAVENDER = ITEMS.register("lavender", () -> new BlockItem(SCPBlocks.LAVENDER.get(), new Item.Properties()));
    public static final RegistryObject<Item> SCP_500_BOTTLE = ITEMS.register("scp_500_bottle", () -> new SCP500Bottle(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCP_999_BUCKET = ITEMS.register("scp_999_bucket", () -> new SCP999BucketItem(SCPEntities.SCP_999, () -> Fluids.EMPTY, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties().stacksTo(1))));
    public static final RegistryObject<Item> SCP_3199_EGG_BUCKET = ITEMS.register("scp_3199_egg_bucket", () -> new SolidBucketMobItem(Blocks.POWDER_SNOW, SCPEntities.SCP_3199_EGG::get, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CONTAINMENT_CAGE = ITEMS.register("containment_cage", () -> new ContainmentItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> POLAROID = ITEMS.register("polaroid", () -> new Polaroid(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARROW = ITEMS.register("arrow", () -> new InstantDeleteItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> REALITY_SCANNER = ITEMS.register("reality_scanner", () -> new RealityScanner(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> LEVEL_1_KEYCARD = ITEMS.register("keycard_1", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEVEL_2_KEYCARD = ITEMS.register("keycard_2", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEVEL_3_KEYCARD = ITEMS.register("keycard_3", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEVEL_4_KEYCARD = ITEMS.register("keycard_4", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEVEL_5_KEYCARD = ITEMS.register("keycard_5", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEVEL_OMNI_KEYCARD = ITEMS.register("keycard_omni", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SAFE_ACHIEVEMENT = ITEMS.register("safe_achievement", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));
    public static final RegistryObject<Item> EUCLID_ACHIEVEMENT = ITEMS.register("euclid_achievement", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));
    public static final RegistryObject<Item> KETER_ACHIEVEMENT = ITEMS.register("keter_achievement", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));


    public static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityType, Color backgroundColor, Color highlightColor) {
        return ITEMS.register(name, () -> new SCPEggItem(entityType, backgroundColor.getRGB(), highlightColor.getRGB(), new Item.Properties().stacksTo(16)));
    }

}
