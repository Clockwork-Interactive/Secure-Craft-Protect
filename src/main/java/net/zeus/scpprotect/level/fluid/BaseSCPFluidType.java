package net.zeus.scpprotect.level.fluid;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BaseSCPFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final int tintColor;
    private boolean isWater;

    public BaseSCPFluidType(Properties properties, boolean isWater, ResourceLocation stillTexture, ResourceLocation flowingTexture, int tintColor) {
        super(properties);
        this.isWater = isWater;
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.tintColor = tintColor;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return isWater ? UNDERWATER_OVERLAY : IClientFluidTypeExtensions.super.getRenderOverlayTexture(mc);
            }
        });
    }
}
