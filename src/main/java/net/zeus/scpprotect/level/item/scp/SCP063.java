package net.zeus.scpprotect.level.item.scp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.sound.SCPSounds;

public class SCP063 extends Item implements Anomaly {
    public SCP063(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 100;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            HitResult hitResult = this.calculateHitResult(pLivingEntity);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos pos = blockHitResult.getBlockPos();
                BlockState blockState = pLevel.getBlockState(pos);
                if (blockState.getBlock().defaultDestroyTime() >= 0.0F) {
                    pLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    pLevel.playSound(null, pos, blockState.getSoundType().getBreakSound(), SoundSource.BLOCKS);
                }
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (!pLevel.isClientSide && pLivingEntity.tickCount % 10 == 0) {
            pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SCPSounds.SCP_063.get(), pLivingEntity.getSoundSource(), 1.0F, 1.0F);
            HitResult hitResult = this.calculateHitResult(pLivingEntity);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockState blockState = pLevel.getBlockState(blockHitResult.getBlockPos());
                if (blockState.getBlock().defaultDestroyTime() >= 0.0F) {
                    this.spawnDustParticles(pLevel, blockHitResult, blockState);
                }
            }
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BRUSH;
    }

    private HitResult calculateHitResult(LivingEntity pEntity) {
        return ProjectileUtil.getHitResultOnViewVector(pEntity, (entity) -> !entity.isSpectator() && entity.isPickable(), 2.0D);
    }

    public void spawnDustParticles(Level pLevel, BlockHitResult pHitResult, BlockState pState) {
        if (pLevel instanceof ServerLevel serverLevel) {
            int j = pLevel.getRandom().nextInt(7, 12);
            BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.BLOCK, pState);
            Vec3 vec3 = pHitResult.getLocation();

            for (int k = 0; k < j; ++k) {
                serverLevel.sendParticles(blockparticleoption, vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_063;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }
}