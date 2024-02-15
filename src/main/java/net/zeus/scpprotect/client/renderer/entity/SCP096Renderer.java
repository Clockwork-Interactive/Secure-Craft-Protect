package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP096Model;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP096Renderer extends GeoEntityRenderer<SCP096> {

    public SCP096Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP096Model());
        this.shadowRadius = 0.5F;
    }

}
