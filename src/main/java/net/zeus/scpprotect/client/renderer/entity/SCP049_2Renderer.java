package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP049_2Model;
import net.zeus.scpprotect.level.entity.entities.SCP049_2;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP049_2Renderer extends GeoEntityRenderer<SCP049_2> {
    public SCP049_2Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP049_2Model());
        this.shadowRadius = 0.5F;
    }
}
