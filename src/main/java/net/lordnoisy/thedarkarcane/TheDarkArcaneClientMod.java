package net.lordnoisy.thedarkarcane;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.client.SkinnedCowRenderer;
import net.lordnoisy.thedarkarcane.particle.ModParticles;
import net.lordnoisy.thedarkarcane.particle.custom.BloodParticle;

public class TheDarkArcaneClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TheDarkArcane.LOGGER.info("TheDA On Init Client " + TheDarkArcane.MOD_ID);
        EntityRendererRegistry.register(ModEntities.SKINNED_COW, SkinnedCowRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLOOD_PARTICLE, BloodParticle.Factory::new);
    }
}
