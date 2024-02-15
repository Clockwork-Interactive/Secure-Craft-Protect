package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP811Model;
import net.zeus.scpprotect.level.entity.entities.SCP811;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP811Renderer extends GeoEntityRenderer<SCP811> {

    public SCP811Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP811Model());
    }

}
