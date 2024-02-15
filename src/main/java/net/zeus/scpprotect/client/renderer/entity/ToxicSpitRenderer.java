package net.zeus.scpprotect.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.zeus.scpprotect.client.models.entity.ToxicSpitModel;
import net.zeus.scpprotect.level.entity.entities.projectiles.ToxicSpit;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ToxicSpitRenderer extends GeoEntityRenderer<ToxicSpit> {
    public ToxicSpitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToxicSpitModel());
    }
}
