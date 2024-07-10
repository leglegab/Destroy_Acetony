package com.petrolpark.destroy.client.ponder.particle;

import java.util.Random;

import com.petrolpark.destroy.client.particle.RainParticle;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.AABB;

public class DestroyEmitters {

    private static final Random RANDOM = new Random();
    
    public static final <T extends ParticleOptions> Emitter rain(AABB aabb, double r, double g, double b) {
        return (w, x, y, z) -> w.addParticle(
            new RainParticle.Data(),
            x + aabb.minX + RANDOM.nextFloat() * aabb.getXsize(),
            z + aabb.minY + RANDOM.nextFloat() * aabb.getYsize(),
            z + aabb.minZ + RANDOM.nextFloat() * aabb.getZsize(),
            r, g, b
        );
    };
};
