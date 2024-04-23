package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP131Model;
import net.zeus.scpprotect.level.entity.entities.SCP131;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP131Renderer extends GeoEntityRenderer<SCP131> {
    public SCP131Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP131Model());
        this.shadowRadius = 0.4F;
    }
}
