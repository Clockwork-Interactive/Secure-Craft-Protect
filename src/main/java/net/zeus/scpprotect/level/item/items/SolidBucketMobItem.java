package net.zeus.scpprotect.level.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class SolidBucketMobItem extends SolidBucketItem {

    private final Supplier<? extends EntityType<?>> entityTypeSupplier;

    public SolidBucketMobItem(Block pBlock, Supplier<EntityType<?>> entityType, SoundEvent pPlaceSound, Properties pProperties) {
        super(pBlock, pPlaceSound, pProperties);
        this.entityTypeSupplier = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        ItemStack container = pContext.getItemInHand();
        Entity player = pContext.getPlayer();
        if (level instanceof ServerLevel serverLevel && player != null) {
            this.spawn(serverLevel, container, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
        return super.useOn(pContext);
    }

    private void spawn(ServerLevel pServerLevel, ItemStack pBucketedMobStack, BlockPos pPos) {
        Entity entity = getType().spawn(pServerLevel, pBucketedMobStack, null, pPos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            bucketable.loadFromBucketTag(pBucketedMobStack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }

    }

    protected EntityType<?> getType() {
        return entityTypeSupplier.get();
    }

}
