package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP058Model;
import net.zeus.scpprotect.client.models.entity.SCP939Model;
import net.zeus.scpprotect.level.entity.entities.SCP058;
import net.zeus.scpprotect.level.entity.entities.SCP939;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP058Renderer extends GeoEntityRenderer<SCP058> {
    public SCP058Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP058Model());
        this.shadowRadius = 0.5F;
    }

}
