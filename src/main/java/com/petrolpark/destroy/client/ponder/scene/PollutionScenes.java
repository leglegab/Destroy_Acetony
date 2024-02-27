package com.petrolpark.destroy.client.ponder.scene;

import com.petrolpark.destroy.chemistry.Mixture;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.petrolpark.destroy.client.particle.DestroyParticleTypes;
import com.petrolpark.destroy.client.particle.data.GasParticleData;
import com.petrolpark.destroy.fluid.MixtureFluid;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class PollutionScenes {
    
    public static final void pipesAndTanks(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("pollution.tanks", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Emitter emitter = Emitter.simple(new GasParticleData(DestroyParticleTypes.EVAPORATION.get(), MixtureFluid.of(100, Mixture.pure(DestroyMolecules.NITROGEN_DIOXIDE))), new Vec3(0d, 0.05d, 0d));
        BlockPos tank = util.grid.at(3, 1, 2);
        Vec3 pipeTop = util.vector.topOf(1, 2, 2);

        scene.world.propagatePipeChange(util.grid.at(2, 1, 2));
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(pipeTop)
            .attachKeyFrame();
        for (int i = 0; i < 6; i ++) {
            scene.effects.emitParticles(pipeTop, emitter, 1f, 1);
        };

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(tank, Direction.NORTH))
            .attachKeyFrame();
        scene.idle(20);
        scene.world.destroyBlock(tank);
        scene.effects.emitParticles(util.vector.centerOf(tank), emitter, 1f, 1);
        scene.idle(100);

        scene.markAsFinished();;
    };

    public static final void basinsAndVats(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("pollution.basins_and_vats", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 9);
        scene.showBasePlate();
    };
};
