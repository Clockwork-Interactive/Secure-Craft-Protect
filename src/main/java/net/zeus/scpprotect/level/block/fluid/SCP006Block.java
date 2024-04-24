package net.zeus.scpprotect.level.block.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.zeus.scpprotect.util.Misc;

import java.util.function.Supplier;

public class SCP006Block extends LiquidBlock {
    public SCP006Block(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity entity) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Misc.TPS, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Misc.TPS, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, Misc.TPS, 2));
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(64) == 0) {
            pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, pRandom.nextFloat() * 0.25F + 0.75F, pRandom.nextFloat() + 0.5F, false);
        }
        if (pRandom.nextInt(10) == 0) {
            pLevel.addParticle(ParticleTypes.UNDERWATER, (double)pPos.getX() + pRandom.nextDouble(), (double)pPos.getY() + pRandom.nextDouble(), (double)pPos.getZ() + pRandom.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }
}
