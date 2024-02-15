package net.zeus.scpprotect.level.entity.entities;

import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.level.entity.ModEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP3199Egg extends LivingEntity implements GeoEntity {

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SCP3199Egg(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.ATTACK_DAMAGE, 0.0F)
                .add(Attributes.ATTACK_SPEED, 0.0F)
                .add(Attributes.MAX_HEALTH, 10.0F)
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
    public void tick() {
        super.tick();
        if (this.tickCount % 3600 == 0) {
            SCP3199 scp3199 = new SCP3199(ModEntity.SCP_3199.get(), this.level());
            scp3199.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(scp3199);
            this.discard();
        }
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

}
