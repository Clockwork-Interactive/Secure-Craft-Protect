package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP096Model;
import net.zeus.scpprotect.level.entity.custom.SCP096;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP096EntityRenderer extends GeoEntityRenderer<SCP096> {

    public SCP096EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP096Model());
        this.shadowRadius = 0.5F;
    }

}
