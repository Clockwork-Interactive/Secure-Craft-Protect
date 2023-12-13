package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP173IModel;
import net.zeus.scpprotect.level.entity.custom.SCP173i;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP173IEntityRenderer extends GeoEntityRenderer<SCP173i> {

    public SCP173IEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP173IModel());
        this.shadowRadius = 0.5F;
    }

}
