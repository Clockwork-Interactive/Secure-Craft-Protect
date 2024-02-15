package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP3199EggModel;
import net.zeus.scpprotect.level.entity.entities.SCP3199Egg;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP3199EggRenderer extends GeoEntityRenderer<SCP3199Egg> {

    public SCP3199EggRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP3199EggModel());

    }
}
