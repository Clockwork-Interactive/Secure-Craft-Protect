package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP106Model;
import net.zeus.scpprotect.level.entity.entities.SCP106;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP106Renderer extends GeoEntityRenderer<SCP106> {

    public SCP106Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP106Model());
    }

}
