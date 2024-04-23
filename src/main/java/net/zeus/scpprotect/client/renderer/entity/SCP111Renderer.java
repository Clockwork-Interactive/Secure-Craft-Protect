package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP111Model;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP111Renderer extends GeoEntityRenderer<SCP111> {
    public SCP111Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP111Model());
        this.shadowRadius = 0.4F;
    }
}
