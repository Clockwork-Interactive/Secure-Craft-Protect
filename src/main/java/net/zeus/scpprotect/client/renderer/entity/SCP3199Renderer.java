package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP3199Model;
import net.zeus.scpprotect.level.entity.entities.SCP3199;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP3199Renderer extends GeoEntityRenderer<SCP3199> {

    public SCP3199Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP3199Model());
    }

}
