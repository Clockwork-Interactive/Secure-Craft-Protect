package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP939Model;
import net.zeus.scpprotect.level.entity.entities.SCP939;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP939Renderer extends GeoEntityRenderer<SCP939> {

    public SCP939Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP939Model());
        this.shadowRadius = 0.7F;
    }

}
