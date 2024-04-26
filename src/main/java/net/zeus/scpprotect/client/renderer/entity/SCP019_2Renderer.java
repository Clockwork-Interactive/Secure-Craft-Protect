package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP019_2Model;
import net.zeus.scpprotect.level.entity.entities.SCP019_2;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP019_2Renderer extends GeoEntityRenderer<SCP019_2> {
    public SCP019_2Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP019_2Model());
        this.shadowRadius = 0.5F;
    }
}
