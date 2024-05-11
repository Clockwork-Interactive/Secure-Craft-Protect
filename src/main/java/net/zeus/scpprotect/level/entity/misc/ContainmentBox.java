package net.zeus.scpprotect.level.entity.misc;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.zeus.scpprotect.level.entity.SCPEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ContainmentBox extends Animal implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final EntityDataAccessor<String> ENTITY_ID = SynchedEntityData.defineId(ContainmentBox.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<CompoundTag> SAVED_DATA = SynchedEntityData.defineId(ContainmentBox.class, EntityDataSerializers.COMPOUND_TAG);
    private EntityType<?> heldEntityType;
    private Entity heldEntity;

    public ContainmentBox(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ContainmentBox(Level pLevel, Entity entity) {
        super(SCPEntities.CONTAINMENT_BOX.get(), pLevel);
        this.containEntity(entity);
    }

    public ContainmentBox(Level pLevel) {
        super(SCPEntities.CONTAINMENT_BOX.get(), pLevel);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ENTITY_ID, "");
        this.entityData.define(SAVED_DATA, new CompoundTag());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.ATTACK_DAMAGE, 0.0F)
                .add(Attributes.ATTACK_SPEED, 0.0F)
                .add(Attributes.MAX_HEALTH, 10.0F)
                .add(Attributes.FOLLOW_RANGE, 0.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100.0F)
                .add(ForgeMod.ENTITY_REACH.get(), 0.0F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 0.0F);
    }

    private void setSavedData(CompoundTag tag) {
        this.entityData.set(SAVED_DATA, tag);
    }

    private CompoundTag getSavedData() {
        return this.entityData.get(SAVED_DATA);
    }

    public void containEntity(Entity entity) {
        this.heldEntityType = entity.getType();
        this.setSavedData(entity.saveWithoutId(new CompoundTag()));

        String[] split = this.heldEntityType.toString().split("[.]");
        String id = "%s:%s".formatted(split[1], split[2]);
        this.setEntityId(id);
    }

    public void releaseEntity() {
        Entity entity = this.getHeldEntity();
        if (entity != null) {
            this.loadData(this.getSavedData(), entity);
            entity.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(entity);
        }
        this.discard();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pPlayer.isCrouching()) {
            this.releaseEntity();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Nullable
    public Entity getHeldEntity() {
        if (this.getEntityId().isEmpty()) return null;

        if (this.heldEntityType == null) {
            this.heldEntityType = EntityType.byString(this.getEntityId()).orElse(null);
        }
        if (this.heldEntityType == null) return null;
        boolean firstTime = this.heldEntity == null;
        Entity held = this.heldEntity == null ? this.heldEntity = this.heldEntityType.create(this.level()) : this.heldEntity;
        if (held != null && firstTime) {
            this.loadData(this.getSavedData(), held);
        }
        return held;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.heldEntityType != null) {
            ListTag listTag = new ListTag();
            listTag.add(this.getSavedData());
            this.getSavedData().putString("id", this.getEntityId());
            this.setSavedData(this.getSavedData()); // I know it looks weird
            pCompound.put("HeldEntity", listTag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        ListTag listTag = pCompound.getList("HeldEntity", Tag.TAG_COMPOUND);

        if (!listTag.isEmpty()) {
            this.setSavedData(listTag.getCompound(0));
            this.heldEntityType = EntityType.byString(this.getSavedData().getString("id")).orElse(null);
            this.setEntityId(this.getSavedData().getString("id"));
        }

        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return pSource.is(DamageTypes.GENERIC_KILL) && super.hurt(pSource, pAmount);
    }

    public String getEntityId() {
        return this.entityData.get(ENTITY_ID);
    }

    public void setEntityId(String id) {
        this.entityData.set(ENTITY_ID, id);
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

    private void loadData(CompoundTag tag, Entity entity) {
        if (tag != null)
            entity.load(tag);
    }

}
