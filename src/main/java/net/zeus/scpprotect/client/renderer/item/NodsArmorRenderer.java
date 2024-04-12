package net.zeus.scpprotect.client.renderer.item;

import net.zeus.scpprotect.client.models.item.NodsModel;
import net.zeus.scpprotect.level.item.armor.NightVisionGoggles;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class NodsArmorRenderer extends GeoArmorRenderer<NightVisionGoggles> {

    public NodsArmorRenderer() {
        super(new NodsModel());
    }

}
