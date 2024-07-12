package com.petrolpark.destroy.client.ponder.scene;

import com.petrolpark.destroy.client.ponder.instruction.CameraShakeInstruction;
import com.petrolpark.destroy.client.ponder.particle.DestroyEmitters;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;

public class KineticsScenes {
    
    public static void colossalCogwheel(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("colossal_cogwheel", "This text is defined in a language file.");
        scene.configureBasePlate(1, 1, 5);
        scene.scaleSceneView(0.75f);
        scene.showBasePlate();
        scene.idle(20);

        Selection innerCogs = util.select.fromTo(2, 1, 2, 4, 1, 4);
        Selection colossalCog = util.select.fromTo(1, 1, 1, 5, 1, 5).substract(innerCogs);
        BlockPos largeCog = util.grid.at(2, 1, 6);
        Selection firstLargeCog = util.select.position(largeCog);
        Selection otherLargeCogs = util.select.fromTo(0, 1, 0, 6, 1, 6).substract(colossalCog).substract(innerCogs).substract(firstLargeCog);
        Selection keepingLargeCogs = util.select.position(4, 1, 0).add(util.select.position(6, 1, 2));
        BlockPos controller = util.grid.at(3, 1, 5);
        BlockPos center = util.grid.at(3, 1, 3);
        BlockPos smallCog = util.grid.at(1, 1, 1);
        BlockPos innerCog = util.grid.at(2, 1, 3);

        scene.overlay.showText(40)
            .text("This text is defined in a language file.")
            .independent();
        scene.idle(60);
        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .independent();
        scene.idle(80);
        scene.world.showSection(util.select.fromTo(0, 0, 0, 6, 0, 6).substract(util.select.fromTo(1, 0, 1, 5, 0, 5)), Direction.UP);
        scene.idle(20);
        scene.world.showSection(util.select.position(2, 0, 7), Direction.NORTH);
        scene.idle(20);
        scene.world.showSection(firstLargeCog, Direction.DOWN);
        scene.idle(54);

        ElementLink<WorldSectionElement> cogwheel = scene.world.showIndependentSection(colossalCog, Direction.DOWN);
        scene.world.moveSection(cogwheel, util.vector.of(0d, 10, 0d), 0);
        scene.idle(1);
        scene.addKeyframe();
        scene.world.moveSection(cogwheel, util.vector.of(0d, -10d, 0d), 5);
        scene.idle(5);
        scene.effects.emitParticles(util.vector.of(1d, 1d, 1d), DestroyEmitters.inAABB(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, new AABB(0d, 0.1d, 0d, 5d, 0.2d, 5d), util.vector.of(0d, 0.1d, 0d)), 20f, 1);
        scene.addInstruction(new CameraShakeInstruction());
        scene.idle(40);

        scene.overlay.showText(160)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(1, 1, 3));
        scene.idle(40);
        scene.effects.rotationDirectionIndicator(controller, center.above());
        scene.effects.rotationDirectionIndicator(largeCog);
        scene.idle(80);
        scene.world.showSection(otherLargeCogs, Direction.DOWN);
        scene.idle(60);

        scene.world.hideSection(otherLargeCogs.copy().substract(keepingLargeCogs), Direction.UP);
        scene.idle(20);
        ElementLink<WorldSectionElement> smallCogs = scene.world.showIndependentSection(util.select.position(1, 2, 1).add(util.select.position(5, 2, 5)), Direction.DOWN);
        scene.world.moveSection(smallCogs, util.vector.of(0d, -1d, 0d), 0);
        scene.idle(20);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.topOf(smallCog));
        scene.idle(40);
        scene.effects.rotationDirectionIndicator(controller.above(), center);
        scene.effects.rotationDirectionIndicator(smallCog);
        scene.idle(80);

        scene.world.showSection(innerCogs, Direction.DOWN);
        scene.idle(20);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.topOf(innerCog));
        scene.idle(40);
        scene.effects.rotationDirectionIndicator(innerCog);
        scene.idle(80);


        scene.markAsFinished();
    };
};
