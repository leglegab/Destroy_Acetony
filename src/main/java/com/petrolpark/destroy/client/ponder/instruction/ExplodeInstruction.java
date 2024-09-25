package com.petrolpark.destroy.client.ponder.instruction;

import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;

import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplodeInstruction extends PonderInstruction {

    public final ExplosionFactory explosionFactory;

    public ExplodeInstruction(ExplosionFactory explosion) {
        this.explosionFactory = explosion;
    };

    @Override
    public boolean isComplete() {
        return true;
    };

    @Override
    public void tick(PonderScene scene) {
        Explosion explosion = explosionFactory.create(scene.getWorld());
        explosion.explode();
        explosion.finalizeExplosion(true);
    };

    @FunctionalInterface
    public static interface ExplosionFactory {

        public Explosion create(Level level);
    };
    
};
