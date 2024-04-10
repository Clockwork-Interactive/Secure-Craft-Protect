package net.zeus.scpprotect.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.entity.custom.SCP019BlockEntity;
import net.zeus.scpprotect.level.block.entity.custom.SCP310BlockEntity;
import net.zeus.scpprotect.level.block.entity.custom.SCP330BlockEntity;

public class SCPBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SCP.MOD_ID);

    public static final RegistryObject<BlockEntityType<SCP019BlockEntity>> SCP_019_BE = register("scp_019_be", SCPBlocks.SCP_019, SCP019BlockEntity::new);
    public static final RegistryObject<BlockEntityType<SCP330BlockEntity>> SCP_330_BE = register("scp_330_be", SCPBlocks.SCP_330, SCP330BlockEntity::new);
    public static final RegistryObject<BlockEntityType<SCP310BlockEntity>> SCP_310_BE = register("scp_310_be", SCPBlocks.SCP_310, SCP310BlockEntity::new);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, RegistryObject<Block> registryObject, BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory) {
        RegistryObject<? extends BlockEntityType<? extends BlockEntity>> ret = BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, registryObject.get()).build(null));
        return (RegistryObject<BlockEntityType<T>>) ret;
    }
}
