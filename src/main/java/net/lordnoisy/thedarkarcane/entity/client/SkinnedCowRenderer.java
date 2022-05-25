package net.lordnoisy.thedarkarcane.entity.client;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SkinnedCowRenderer extends GeoEntityRenderer<SkinnedCowEntity> {
    public SkinnedCowRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SkinnedCowModel());
    }

    @Override
    public Identifier getTextureLocation(SkinnedCowEntity instance) {
        return new Identifier(TheDarkArcane.MOD_ID, "textures/entity/skinned_cow/skinned_cow.png");
    }
}
