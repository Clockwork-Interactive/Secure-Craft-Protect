package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.RandomSource;
import net.zeus.scpprotect.client.models.entity.SCP939Model;
import net.zeus.scpprotect.level.entity.custom.SCP939;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP939EntityRenderer extends GeoEntityRenderer<SCP939> {

    RandomSource randomSource = RandomSource.create();

    public SCP939EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP939Model());
        this.shadowRadius = 0.5F;
    }

}
