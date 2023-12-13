package net.zeus.scpprotect.level.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GenericParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected GenericParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float v, SpriteSet set, SpriteSet sprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.sprites = set;
        this.friction = 0.8F;
        this.lifetime = 100;
        this.setSpriteFromAge(set);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

        this.gravity = 2F;

    }

    public void tick() {
        super.tick();
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new GenericParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, 1.0F, this.sprites, sprites);
        }
    }
}
