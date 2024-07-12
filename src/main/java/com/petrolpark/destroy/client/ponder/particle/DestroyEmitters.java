package com.petrolpark.destroy.client.ponder.particle;

import java.util.Random;

import com.petrolpark.destroy.client.particle.RainParticle;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DestroyEmitters {

    private static final Random RANDOM = new Random();

    private static final <T extends ParticleOptions> Emitter inAABB(T data, AABB aabb, double vx, double vy, double vz) {
        return (w, x, y, z) -> w.addParticle(
            data,
            x + aabb.minX + RANDOM.nextFloat() * aabb.getXsize(),
            z + aabb.minY + RANDOM.nextFloat() * aabb.getYsize(),
            z + aabb.minZ + RANDOM.nextFloat() * aabb.getZsize(),
            vx, vy, vz
        );
    };

    public static final <T extends ParticleOptions> Emitter inAABB(T data, AABB aabb, Vec3 velocity) {
        return inAABB(data, aabb, velocity.x, velocity.y, velocity.z);
    };
    
    public static final <T extends ParticleOptions> Emitter rain(AABB aabb, double r, double g, double b) {
        return inAABB(new RainParticle.Data(), aabb, r, g, b);
    };

};
