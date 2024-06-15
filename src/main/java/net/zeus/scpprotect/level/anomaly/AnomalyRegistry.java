package net.zeus.scpprotect.level.anomaly;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.creator.AnomalyType;
import net.zeus.scpprotect.level.anomaly.creator.BlockAnomalyType;
import net.zeus.scpprotect.level.anomaly.creator.EntityAnomalyType;
import net.zeus.scpprotect.level.anomaly.creator.ItemAnomalyType;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.entity.SCPEntities;
import net.zeus.scpprotect.level.entity.entities.*;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.item.scp.SCP207;
import net.zeus.scpprotect.level.item.scp.SCP500Bottle;

import java.util.HashMap;

public class AnomalyRegistry {

    public static final HashMap<String, AnomalyType<?, ?>> ANOMALY_TYPES = new HashMap<>();

    /**
     * Entity SCPs
     */
    public static final EntityAnomalyType<SCP058> SCP_058 = new EntityAnomalyType<>(SCPEntities.SCP_058, SCP.SCPTypes.KETER, SCP.SCPNames.SCP_058);
    public static final EntityAnomalyType<SCP096> SCP_096 = new EntityAnomalyType<>(SCPEntities.SCP_096, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_096);
    public static final EntityAnomalyType<SCP131> SCP_131 = new EntityAnomalyType<>(SCPEntities.SCP_131, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_131, 2);
    public static final EntityAnomalyType<SCP173> SCP_173 = new EntityAnomalyType<>(SCPEntities.SCP_173, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_173);
    public static final EntityAnomalyType<SCP346> SCP_346 = new EntityAnomalyType<>(SCPEntities.SCP_346, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_346);
    public static final EntityAnomalyType<SCP939> SCP_939 = new EntityAnomalyType<>(SCPEntities.SCP_939, SCP.SCPTypes.KETER, SCP.SCPNames.SCP_939, 5);
    public static final EntityAnomalyType<SCP966> SCP_966 = new EntityAnomalyType<>(SCPEntities.SCP_966, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_966);
    public static final EntityAnomalyType<SCP999> SCP_999 = new EntityAnomalyType<>(SCPEntities.SCP_999, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_999);
    public static final EntityAnomalyType<SCP019_2> SCP_019_2 = new EntityAnomalyType<>(SCPEntities.SCP_019_2, SCP.SCPTypes.KETER, SCP.SCPNames.UNDEFINED);
    public static final EntityAnomalyType<SCP811> SCP_811 = new EntityAnomalyType<>(SCPEntities.SCP_811, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_811);
    public static final EntityAnomalyType<SCP3199> SCP_3199 = new EntityAnomalyType<>(SCPEntities.SCP_3199, SCP.SCPTypes.KETER, SCP.SCPNames.SCP_3199, 3);
    public static final EntityAnomalyType<SCP3199Egg> SCP_3199_EGG = new EntityAnomalyType<>(SCPEntities.SCP_3199_EGG, SCP.SCPTypes.KETER, SCP.SCPNames.UNDEFINED);
    public static final EntityAnomalyType<SCP111> SCP_111 = new EntityAnomalyType<>(SCPEntities.SCP_111, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_111, 4);
    public static final EntityAnomalyType<SCP049> SCP_049 = new EntityAnomalyType<>(SCPEntities.SCP_049, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_049);
    public static final EntityAnomalyType<SCP049_2> SCP_049_2 = new EntityAnomalyType<>(SCPEntities.SCP_049_2, SCP.SCPTypes.EUCLID, SCP.SCPNames.UNDEFINED);
    public static final EntityAnomalyType<SCP106> SCP_106 = new EntityAnomalyType<>(SCPEntities.SCP_106, SCP.SCPTypes.KETER, SCP.SCPNames.SCP_106);
    public static final EntityAnomalyType<Rebel> REBEL = new EntityAnomalyType<>(SCPEntities.REBEL, SCP.SCPTypes.KETER, SCP.SCPNames.UNDEFINED);

    /**
     * Block SCPs
     */
    public static final BlockAnomalyType<Block> SCP_310 = new BlockAnomalyType<>(SCPBlocks.SCP_310, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_310);
    public static final BlockAnomalyType<Block> SCP_330 = new BlockAnomalyType<>(SCPBlocks.SCP_330, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_330);
    public static final BlockAnomalyType<Block> SCP_019 = new BlockAnomalyType<>(SCPBlocks.SCP_019, SCP.SCPTypes.KETER, SCP.SCPNames.SCP_019);

    /**
     * Item SCPs
     */
    public static final ItemAnomalyType<Item> SCP_006_BUCKET = new ItemAnomalyType<>(SCPItems.SCP_006_BUCKET, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_006);
    public static final ItemAnomalyType<Item> SCP_500_PILL = new ItemAnomalyType<>(SCPItems.SCP_500,() -> {
        ItemStack bottle = new ItemStack(SCPItems.SCP_500_BOTTLE.get());
        ItemStack pills = new ItemStack(SCPItems.SCP_500.get());
        pills.setCount(16);
        SCP500Bottle.add(bottle, pills);
        return bottle;
    }, SCP.SCPTypes.SAFE, SCP.SCPNames.UNDEFINED);
    public static final ItemAnomalyType<Item> SCP_1025 = new ItemAnomalyType<>(SCPItems.SCP_1025, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_1025);
    public static final ItemAnomalyType<Item> SCP_109 = new ItemAnomalyType<>(SCPItems.SCP_109, SCP.SCPTypes.EUCLID, SCP.SCPNames.SCP_109);
    public static final ItemAnomalyType<Item> SCP_207 = new ItemAnomalyType<>(SCPItems.SCP_207, () -> {
        ItemStack bottles = new ItemStack(SCPItems.SCP_207.get());
        bottles.setCount(24);
        return bottles;
    }, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_207);
    public static final ItemAnomalyType<Item> SCP_063 = new ItemAnomalyType<>(SCPItems.SCP_063, SCP.SCPTypes.SAFE, SCP.SCPNames.SCP_063);

    // Static initializer, don't remove 🥰
    public static void init() { }

}
