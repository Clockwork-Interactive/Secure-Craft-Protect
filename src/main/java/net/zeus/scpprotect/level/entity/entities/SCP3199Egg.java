package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.ModEntity;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.ModItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP3199Egg extends Mob implements GeoEntity, Bucketable, Anomaly {

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int timeRemaining = 3600;

    public SCP3199Egg(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.ATTACK_DAMAGE, 0.0F)
                .add(Attributes.ATTACK_SPEED, 0.0F)
                .add(Attributes.MAX_HEALTH, 250.0F)
                .add(Attributes.FOLLOW_RANGE, 0.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2F)
                .add(ForgeMod.NAMETAG_DISTANCE.get(), 0.0F);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity entity = pSource.getEntity();
        if (!(entity instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().getItem() instanceof PickaxeItem pickaxeItem && pickaxeItem.getTier() == Tiers.NETHERITE) && !pSource.is(DamageTypes.GENERIC_KILL))
            return false;
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.timeRemaining <= 0) {
            SCP3199 scp3199 = new SCP3199(ModEntity.SCP_3199.get(), this.level());
            scp3199.setPos(this.getX(), this.getY(), this.getZ());
            scp3199.setBaby();
            this.level().addFreshEntity(scp3199);
            this.discard();
        } else
            this.timeRemaining--;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return pEffectInstance.getEffect() != MobEffects.POISON && super.canBeAffected(pEffectInstance);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return NonNullList.withSize(1, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean pFromBucket) {

    }

    @Override
    public void saveToBucketTag(ItemStack pStack) {
        Bucketable.saveDefaultDataToBucketTag(this, pStack);
        pStack.getOrCreateTag().putInt("time", this.timeRemaining);
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
        if (pTag.contains("time"))
            this.timeRemaining = pTag.getInt("time");
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.SCP_3199_EGG_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_POWDER_SNOW;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.KETER;
    }

}
