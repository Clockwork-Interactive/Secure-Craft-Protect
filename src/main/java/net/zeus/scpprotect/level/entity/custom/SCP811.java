package net.zeus.scpprotect.level.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.custom.goals.SCP811AttackGoal;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SCP811 extends Monster implements GeoEntity, Anomaly {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SCP811(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractFish.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Frog.class, true));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new SCP811AttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 3.0F)
                .add(Attributes.ATTACK_SPEED, 0.5F)
                .add(Attributes.MAX_HEALTH, 30.0F)
                .add(Attributes.FOLLOW_RANGE, 32.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getRandom().nextFloat() > 0.9999F) {
            BlockState blockState = this.level().getBlockState(this.getOnPos());
            if (blockState.getBlock() instanceof BonemealableBlock bonemealableblock) {
                if (bonemealableblock.isValidBonemealTarget(this.level(), this.getOnPos(), blockState, this.level().isClientSide)) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (bonemealableblock.isBonemealSuccess(this.level(), this.getRandom(), this.getOnPos(), blockState)) {
                            bonemealableblock.performBonemeal(serverLevel, this.getRandom(), this.getOnPos(), blockState);
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean wantsToPickUp(ItemStack pStack) {
        FoodProperties foodProperties = pStack.getFoodProperties(this);
        return pStack.isEdible() && foodProperties != null && foodProperties.isMeat();
    }

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {
        ItemStack stack = pItemEntity.getItem();
        if (!stack.getItem().getDescriptionId().contains("cooked")) { // Hacky
            int slimeballs = 0;
            for (int x = 0; x < stack.getCount(); x++) {
                slimeballs += this.getRandom().nextIntBetweenInclusive(1, 2);
            }
            if (slimeballs > 1) {
                ItemStack slimeballStack = new ItemStack(Items.SLIME_BALL, slimeballs);
                this.drop(slimeballStack, false);
            }
            stack.shrink(stack.getCount());
        }
        super.pickUpItem(pItemEntity);
    }

    public void drop(ItemStack pDroppedItem, boolean pDropAround) {
        if (this.level().isClientSide) {
            this.swing(InteractionHand.MAIN_HAND);
        }

        double d0 = this.getEyeY() - (double) 0.3F;
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), d0, this.getZ(), pDroppedItem);
        itementity.setPickUpDelay(40);

        if (pDropAround) {
            float f = this.random.nextFloat() * 0.5F;
            float f1 = this.random.nextFloat() * ((float) Math.PI * 2F);
            itementity.setDeltaMovement(-Mth.sin(f1) * f, 0.2F, Mth.cos(f1) * f);
        } else {
            float f8 = Mth.sin(this.getXRot() * ((float) Math.PI / 180F));
            float f2 = Mth.cos(this.getXRot() * ((float) Math.PI / 180F));
            float f3 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
            float f4 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
            float f5 = this.random.nextFloat() * ((float) Math.PI * 2F);
            float f6 = 0.02F * this.random.nextFloat();
            itementity.setDeltaMovement((double) (-f3 * f2 * 0.3F) + Math.cos(f5) * (double) f6, -f8 * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F, (double) (f4 * f2 * 0.3F) + Math.sin(f5) * (double) f6);
        }
        this.level().addFreshEntity(itementity);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}