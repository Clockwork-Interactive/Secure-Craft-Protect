package net.zeus.scpprotect.level.worldgen.structure.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class SCPPools {

    public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("scprotect:scp_106/start");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> poolsHolderGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = poolsHolderGetter.getOrThrow(Pools.EMPTY);
        Pools.register(context, "scprotect:scp_106/start", new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.legacy("scprotect:scp_106/start"), 1)), StructureTemplatePool.Projection.RIGID));
    }

}
