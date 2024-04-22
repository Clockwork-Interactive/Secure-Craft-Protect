package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.RebelModel;
import net.zeus.scpprotect.level.entity.entities.Rebel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RebelRenderer extends GeoEntityRenderer<Rebel> {

    public RebelRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RebelModel());
    }

}
