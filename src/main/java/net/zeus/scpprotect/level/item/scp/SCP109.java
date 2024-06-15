package net.zeus.scpprotect.level.item.scp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;

import javax.annotation.Nullable;

public class SCP109 extends Item implements Anomaly {
    public SCP109(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (player.isCrouching()) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            BlockPos blockpos2 = canBlockContainFluid(level, blockpos, blockstate) ? blockpos : blockpos1;
            if (this.emptyContents(player, level, blockpos2, blockhitresult, stack)) {
                player.getCooldowns().addCooldown(stack.getItem(), 100);
                return InteractionResultHolder.success(stack);
            }
        } else {
            player.startUsingItem(usedHand);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    public boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult, @Nullable ItemStack container) {
        Fluid content = Fluids.WATER;
        BlockState blockstate = pLevel.getBlockState(pPos);
        Block block = blockstate.getBlock();
        boolean flag = blockstate.canBeReplaced(content);
        boolean flag1 = blockstate.isAir() || flag || block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(pLevel, pPos, blockstate, content);
        java.util.Optional<net.minecraftforge.fluids.FluidStack> containedFluidStack = java.util.Optional.ofNullable(container).flatMap(net.minecraftforge.fluids.FluidUtil::getFluidContained);
        if (!flag1) {
            return pResult != null && emptyContents(pPlayer, pLevel, pResult.getBlockPos().relative(pResult.getDirection()), null, container);
        } else if (containedFluidStack.isPresent() && content.getFluidType().isVaporizedOnPlacement(pLevel, pPos, containedFluidStack.get())) {
            content.getFluidType().onVaporize(pPlayer, pLevel, pPos, containedFluidStack.get());
            return true;
        } else if (pLevel.dimensionType().ultraWarm() && content.is(FluidTags.WATER)) {
            int i = pPos.getX();
            int j = pPos.getY();
            int k = pPos.getZ();
            pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);

            for (int l = 0; l < 8; ++l) {
                pLevel.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
            }

            return true;
        } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(pLevel, pPos, blockstate, content)) {
            ((LiquidBlockContainer) block).placeLiquid(pLevel, pPos, blockstate, ((FlowingFluid) content).getSource(false));
            playEmptySound(pLevel, pPos);
            return true;
        } else {
            if (!pLevel.isClientSide && flag && !blockstate.liquid()) {
                pLevel.destroyBlock(pPos, true);
            }

            if (!pLevel.setBlock(pPos, content.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;
            } else {
                playEmptySound(pLevel, pPos);
                return true;
            }
        }
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_109;
    }


    public static void playEmptySound(Level level, BlockPos pos) {
        RandomSource source = RandomSource.createNewThreadLocalInstance();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, SCPSounds.SCP_109_POUR.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }



    protected boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return (blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, Fluids.WATER)) || blockstate.liquid();
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }
}
