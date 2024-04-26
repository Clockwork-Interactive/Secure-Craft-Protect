package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP049Model;
import net.zeus.scpprotect.level.entity.entities.SCP049;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP049Renderer extends GeoEntityRenderer<SCP049> {
    public SCP049Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP049Model());
        this.shadowRadius = 0.5F;
    }
}
