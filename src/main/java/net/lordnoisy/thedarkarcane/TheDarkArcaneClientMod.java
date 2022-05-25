package net.lordnoisy.thedarkarcane;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.client.SkinnedCowRenderer;

public class TheDarkArcaneClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TheDarkArcane.LOGGER.info("TheDA On Init Client " + TheDarkArcane.MOD_ID);
        EntityRendererRegistry.register(ModEntities.SKINNED_COW, SkinnedCowRenderer::new);
    }
}
