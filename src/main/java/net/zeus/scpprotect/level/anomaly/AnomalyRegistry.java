package net.zeus.scpprotect.level.anomaly;

import net.minecraft.world.item.Item;
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

import java.util.HashMap;

public class AnomalyRegistry {

    public static final HashMap<String, AnomalyType<?, ?>> ANOMALY_TYPES = new HashMap<>();

    /**
     * Entity SCPs
     */
    public static final EntityAnomalyType<SCP058> SCP_058 = new EntityAnomalyType<>(SCPEntities.SCP_058, SCP.SCPTypes.KETER);
    public static final EntityAnomalyType<SCP096> SCP_096 = new EntityAnomalyType<>(SCPEntities.SCP_096, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP131> SCP_131 = new EntityAnomalyType<>(SCPEntities.SCP_131, SCP.SCPTypes.SAFE, 2);
    public static final EntityAnomalyType<SCP173> SCP_173 = new EntityAnomalyType<>(SCPEntities.SCP_173, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP346> SCP_346 = new EntityAnomalyType<>(SCPEntities.SCP_346, SCP.SCPTypes.SAFE);
    public static final EntityAnomalyType<SCP939> SCP_939 = new EntityAnomalyType<>(SCPEntities.SCP_939, SCP.SCPTypes.KETER, 5);
    public static final EntityAnomalyType<SCP966> SCP_966 = new EntityAnomalyType<>(SCPEntities.SCP_966, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP999> SCP_999 = new EntityAnomalyType<>(SCPEntities.SCP_999, SCP.SCPTypes.SAFE);
    public static final EntityAnomalyType<SCP019_2> SCP_019_2 = new EntityAnomalyType<>(SCPEntities.SCP_019_2, SCP.SCPTypes.KETER);
    public static final EntityAnomalyType<SCP811> SCP_811 = new EntityAnomalyType<>(SCPEntities.SCP_811, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP3199> SCP_3199 = new EntityAnomalyType<>(SCPEntities.SCP_3199, SCP.SCPTypes.KETER, 3);
    public static final EntityAnomalyType<SCP3199Egg> SCP_3199_EGG = new EntityAnomalyType<>(SCPEntities.SCP_3199_EGG, SCP.SCPTypes.KETER);
    public static final EntityAnomalyType<SCP111> SCP_111 = new EntityAnomalyType<>(SCPEntities.SCP_111, SCP.SCPTypes.SAFE, 4);
    public static final EntityAnomalyType<SCP049> SCP_049 = new EntityAnomalyType<>(SCPEntities.SCP_049, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP049_2> SCP_049_2 = new EntityAnomalyType<>(SCPEntities.SCP_049_2, SCP.SCPTypes.EUCLID);
    public static final EntityAnomalyType<SCP106> SCP_106 = new EntityAnomalyType<>(SCPEntities.SCP_106, SCP.SCPTypes.KETER);
    public static final EntityAnomalyType<Rebel> REBEL = new EntityAnomalyType<>(SCPEntities.REBEL, SCP.SCPTypes.KETER);

    /**
     * Block SCPs
     */
    public static final BlockAnomalyType<Block> SCP_310 = new BlockAnomalyType<>(SCPBlocks.SCP_310, SCP.SCPTypes.SAFE);
    public static final BlockAnomalyType<Block> SCP_330 = new BlockAnomalyType<>(SCPBlocks.SCP_330, SCP.SCPTypes.SAFE);
    public static final BlockAnomalyType<Block> SCP_019 = new BlockAnomalyType<>(SCPBlocks.SCP_019, SCP.SCPTypes.KETER);

    /**
     * Item SCPs
     */
    public static final ItemAnomalyType<Item> SCP_006_BUCKET = new ItemAnomalyType<>(SCPItems.SCP_006_BUCKET, SCP.SCPTypes.SAFE);
    public static final ItemAnomalyType<Item> SCP_500_PILL = new ItemAnomalyType<>(SCPItems.SCP_500, SCP.SCPTypes.SAFE);
    public static final ItemAnomalyType<Item> SCP_1025 = new ItemAnomalyType<>(SCPItems.SCP_1025, SCP.SCPTypes.SAFE);
    public static final ItemAnomalyType<Item> SCP_109 = new ItemAnomalyType<>(SCPItems.SCP_109, SCP.SCPTypes.EUCLID);

    // Static initializer, don't remove 🥰
    public static void init() { }

}
