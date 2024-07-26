package com.petrolpark.destroy.client.ponder.scene;

import java.util.HashSet;
import java.util.Set;

import com.petrolpark.destroy.block.PeriodicTableBlock;
import com.petrolpark.destroy.block.PeriodicTableBlock.PeriodicTableEntry;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;

public class ChemistryScenes {
    
    public static final void vatConstruction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.construction", "This text is defined in a language file.");
    };

    public static final void vatHeating(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.heating", "This text is defined in a language file.");
    };

    public static final void bunsenBurner(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("bunsen_burner", "This text is defined in a language file.");
    };

    public static final void roomTemperature(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("pollution.room_temperature", "This text is defined in a language file.");
    };

    public static final void vatMonitoring(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.monitoring", "This text is defined in a language file.");
    };

    public static final void colorimeter(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("colorimeter", "This text is defined in a language file.");
    };

    public static final void uv(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("uv", "This text is defined in a language file.");
    };

    public static int[] defaultPositions = new int[]{
                16,  9, 15,  9, 14,  9, 13,  9, 12,  9, 11,  9, 10,  9,  9,  9,  8,  9,  7,  9,  6,  9,  5,  9,  4,  9,  3,  9,  2,  9,
                16,  8, 15,  8, 14,  8, 13,  8, 12,  8, 11,  8, 10,  8,  9,  8,  8,  8,  7,  8,  6,  8,  5,  8,  4,  8,  3,  8,  2,  8,

        17,  6, 16,  6, 15,  6, 14,  6, 13,  6, 12,  6, 11,  6, 10,  6,  9,  6,  8,  6,  7,  6,  6,  6,  5,  6,  4,  6,  3,  6,          1,  6,  0,  6,
        17,  5, 16,  5, 15,  5, 14,  5, 13,  5, 12,  5, 11,  5, 10,  5,  9,  5,  8,  5,  7,  5,  6,  5,  5,  5,  4,  5,  3,  5,          1,  5,  0,  5,
        17,  4, 16,  4, 15,  4, 14,  4, 13,  4, 12,  4, 11,  4, 10,  4,  9,  4,  8,  4,  7,  4,  6,  4,  5,  4,  4,  4,  3,  4,  2,  4,  1,  4,  0,  4,
        17,  3, 16,  3, 15,  3, 14,  3, 13,  3, 12,  3, 11,  3, 10,  3,  9,  3,  8,  3,  7,  3,  6,  3,  5,  3,  4,  3,  3,  3,  2,  3,  1,  3,  0,  3,
        17,  2, 16,  2, 15,  2, 14,  2, 13,  2, 12,  2,                                                                                  1,  2,  0,  2,
        17,  1, 16,  1, 15,  1, 14,  1, 13,  1, 12,  1,                                                                                  1,  1,  0,  1,
        17,  0,                                                                                                                                  0,  0
    };

    public static void periodicTable(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("periodic_table", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 20);
        scene.showBasePlate();

        scene.scaleSceneView(0.5f);
        scene.rotateCameraY(35);

        Set<PeriodicTableEntry> entriesToCycle = new HashSet<>();
        int maxCycles = 1;

        for (int i = 0; i < defaultPositions.length; i += 2) {
            scene.world.setBlock(util.grid.at(18, 10, 3).below(defaultPositions[i + 1]).west(defaultPositions[i]), Blocks.GRAY_CONCRETE.defaultBlockState(), false);
        };

        for (PeriodicTableEntry element : PeriodicTableBlock.ELEMENTS) {
            if (element.blocks().size() != 0) {
                scene.world.setBlock(util.grid.at(18, 10, 3).below(element.y()).west(element.x()), element.blocks().get(0).defaultBlockState(), false);
                if (element.blocks().size() > 1) {
                    entriesToCycle.add(element);
                    maxCycles = Math.max(maxCycles, element.blocks().size());
                };
            };
        };

        for (int i = 0; i < defaultPositions.length; i += 2) {
            scene.world.showSection(util.select.position(util.grid.at(18, 10, 3).below(defaultPositions[i + 1]).west(defaultPositions[i])), Direction.NORTH);
            scene.idle(1);
        };

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.");

        if (maxCycles > 1) {
            for (int i = 0; i < maxCycles; i++) {
                for (PeriodicTableEntry entry : entriesToCycle) {
                    scene.world.setBlock(util.grid.at(18, 11, 2).below(entry.y()).west(entry.y()), entry.blocks().get(i % entry.blocks().size()).defaultBlockState(), false);
                };
                scene.idle(10);
            };
        };
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        scene.markAsFinished();
    };
};
