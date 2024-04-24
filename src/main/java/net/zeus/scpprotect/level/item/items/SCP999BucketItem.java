package net.zeus.scpprotect.level.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SCP999BucketItem extends BucketItem {
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;
    private final Supplier<? extends SoundEvent> emptySoundSupplier;
    private final Supplier<? extends Fluid> fluidSupplier;

    public SCP999BucketItem(Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier, Item.Properties properties) {
        super(fluidSupplier, properties);
        this.fluidSupplier = fluidSupplier;
        this.emptySoundSupplier = soundSupplier;
        this.entityTypeSupplier = entitySupplier;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        ItemStack container = pContext.getItemInHand();
        Entity player = pContext.getPlayer();
        if (level instanceof ServerLevel serverLevel && player != null) {
            this.spawn(serverLevel, container, pos);
            if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative()) {
                container.shrink(1);
                player.setItemSlot(EquipmentSlot.MAINHAND, Items.BUCKET.getDefaultInstance());
            }
            player.playSound(SoundEvents.BUCKET_EMPTY_FISH, 0.7F, 1.0F);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
            return InteractionResult.SUCCESS;
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

    protected SoundEvent getEmptySound() {
        return this.emptySoundSupplier.get();
    }

    public @NotNull Fluid getFluid() {
        return this.fluidSupplier.get();
    }

    protected EntityType<?> getType() {
        return entityTypeSupplier.get();
    }

}
