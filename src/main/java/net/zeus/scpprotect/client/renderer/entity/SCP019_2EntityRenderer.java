package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.SCP019_2Model;
import net.zeus.scpprotect.level.entity.custom.SCP019_2;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SCP019_2EntityRenderer extends GeoEntityRenderer<SCP019_2> {
    public SCP019_2EntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SCP019_2Model());
    }

}
