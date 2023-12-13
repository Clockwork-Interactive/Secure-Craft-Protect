package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP811Model;
import net.zeus.scpprotect.level.entity.custom.SCP811;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP811EntityRenderer extends GeoEntityRenderer<SCP811> {

    public SCP811EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP811Model());
    }

}
