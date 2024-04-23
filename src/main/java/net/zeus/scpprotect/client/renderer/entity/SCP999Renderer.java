package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP111Model;
import net.zeus.scpprotect.client.models.entity.SCP999Model;
import net.zeus.scpprotect.level.entity.entities.SCP111;
import net.zeus.scpprotect.level.entity.entities.SCP999;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP999Renderer extends GeoEntityRenderer<SCP999> {
    public SCP999Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP999Model());
        this.shadowRadius = 0.4F;
    }
}
