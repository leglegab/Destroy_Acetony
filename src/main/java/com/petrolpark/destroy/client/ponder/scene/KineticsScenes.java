package com.petrolpark.destroy.client.ponder.scene;

import com.petrolpark.destroy.client.ponder.instruction.CameraShakeInstruction;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;

public class KineticsScenes {
    
    public static void colossalCogwheel(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("colossal_cogwheel", "This text is defined in a language file.");
        scene.idle(20);

        scene.addInstruction(new CameraShakeInstruction());
        scene.idle(40);

        scene.markAsFinished();
    };
};
