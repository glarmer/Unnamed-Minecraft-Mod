package net.lordnoisy.thedarkarcane.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(TheDarkArcane.MOD_ID, "blood_particle"),
                BLOOD_PARTICLE);
    }
}
