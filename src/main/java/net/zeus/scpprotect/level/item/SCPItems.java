package net.zeus.scpprotect.level.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.item.armor.Nods;
import net.zeus.scpprotect.level.item.items.*;
import net.zeus.scpprotect.level.item.scp.SCP109;

import java.awt.*;
import java.util.function.Supplier;

public class SCPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SCP.MOD_ID);

    // SCP ITEMS
    public static final RegistryObject<Item> SCP_109 = ITEMS.register("scp_109", () -> new SCP109(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CANDY_RED = ITEMS.register("red_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_BLUE = ITEMS.register("blue_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_GREEN = ITEMS.register("green_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_YELLOW = ITEMS.register("yellow_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_ORANGE = ITEMS.register("orange_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> CANDY_PURPLE = ITEMS.register("purple_candy", () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.CANDY)));
    public static final RegistryObject<Item> BOOK_OF_CHANGE = ITEMS.register("awcy", () -> new BookOfChange(new Item.Properties().stacksTo(1)));

    // SPAWN EGGS
    public static final RegistryObject<Item> SCP_096_SPAWN_EGG = registerEgg("scp_096_spawn_egg", ModEntity.SCP_096, new Color(245, 233, 229), new Color(237, 212, 197), SCP.SCPTypes.EUCLID);
    public static final RegistryObject<Item> SCP_173_SPAWN_EGG = registerEgg("scp_173_spawn_egg", ModEntity.SCP_173, new Color(227, 185, 129), new Color(162, 119, 78, 255), SCP.SCPTypes.EUCLID);
    public static final RegistryObject<Item> SCP_939_SPAWN_EGG = registerEgg("scp_939_spawn_egg", ModEntity.SCP_939, new Color(249, 6, 12), new Color(42, 0, 0), SCP.SCPTypes.KETER);
    public static final RegistryObject<Item> SCP_966_SPAWN_EGG = registerEgg("scp_966_spawn_egg", ModEntity.SCP_966, new Color(193, 163, 163), new Color(232, 180, 180), SCP.SCPTypes.EUCLID);
    public static final RegistryObject<Item> SCP_811_SPAWN_EGG = registerEgg("scp_811_spawn_egg", ModEntity.SCP_811, new Color(40, 103, 54), new Color(50, 148, 89), SCP.SCPTypes.EUCLID);
    public static final RegistryObject<Item> SCP_3199_SPAWN_EGG = registerEgg("scp_3199_spawn_egg", ModEntity.SCP_3199, new Color(238, 203, 201), new Color(183, 131, 131), SCP.SCPTypes.KETER);


    // ARMOR
    public static final RegistryObject<Item> NODS = ITEMS.register("nods", () -> new Nods(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));

    // MISC
    public static final RegistryObject<Item> HAIRBRUSH = ITEMS.register("hairbrush", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLOCKS_TAB_ICON = ITEMS.register("blocks_tab_icon", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));
    public static final RegistryObject<Item> ENTITIES_TAB_ICON = ITEMS.register("entities_tab_icon", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));
    public static final RegistryObject<Item> ITEMS_TAB_ICON = ITEMS.register("items_tab_icon", () -> new InstantDeleteItem(new Item.Properties().stacksTo(0)));
    public static final RegistryObject<Item> CONTAINMENT_ITEM = ITEMS.register("containment_item", () -> new ContainmentItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCP_3199_EGG_BUCKET = ITEMS.register("scp_3199_egg_bucket", () -> new SolidBucketMobItem(Blocks.POWDER_SNOW, ModEntity.SCP_3199_EGG::get, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().stacksTo(1)));

    public static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityType, Color backgroundColor, Color highlightColor, SCP.SCPTypes types) {
        return ITEMS.register(name, () -> new SCPEggItem(entityType, backgroundColor.getRGB(), highlightColor.getRGB(), new Item.Properties().stacksTo(16), types));
    }

}
