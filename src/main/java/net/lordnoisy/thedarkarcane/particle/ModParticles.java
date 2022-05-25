package net.lordnoisy.thedarkarcane.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModParticles {
    public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(TheDarkArcane.MOD_ID, "blood_particle"),
                BLOOD_PARTICLE);
    }

    public static void spawnParticle(World world, Vec3d pos, ParticleEffect particle, Vec3d vel) {

        if (world.isClient) {
            world.addParticle(particle, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
        } else {
            if (world instanceof ServerWorld) {
                ServerWorld sw = (ServerWorld) world;
                sw.spawnParticles(particle, pos.x, pos.y, pos.z, 1, vel.x, vel.y, vel.z, 0);
            }
        }
    }
}
