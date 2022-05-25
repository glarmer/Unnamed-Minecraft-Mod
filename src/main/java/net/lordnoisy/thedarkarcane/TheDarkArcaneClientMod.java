package net.lordnoisy.thedarkarcane;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.client.SkinnedCowRenderer;
import net.lordnoisy.thedarkarcane.fluid.ModFluids;
import net.lordnoisy.thedarkarcane.particle.ModParticles;
import net.lordnoisy.thedarkarcane.particle.custom.BloodParticle;

public class TheDarkArcaneClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TheDarkArcane.LOGGER.info("TheDA On Init Client " + TheDarkArcane.MOD_ID);
        EntityRendererRegistry.register(ModEntities.SKINNED_COW, SkinnedCowRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLOOD_PARTICLE, BloodParticle.Factory::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.BLOOD_STILL,
                new SimpleFluidRenderHandler(SimpleFluidRenderHandler.WATER_STILL,
                        SimpleFluidRenderHandler.WATER_FLOWING,
                        SimpleFluidRenderHandler.WATER_OVERLAY, 0xcc2e23));
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.BLOOD_FLOWING,
                new SimpleFluidRenderHandler(SimpleFluidRenderHandler.WATER_STILL,
                        SimpleFluidRenderHandler.WATER_FLOWING,
                        SimpleFluidRenderHandler.WATER_OVERLAY, 0xcc2e23));
    }
}
