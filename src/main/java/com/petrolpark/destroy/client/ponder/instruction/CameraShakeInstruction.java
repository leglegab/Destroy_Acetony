package com.petrolpark.destroy.client.ponder.instruction;

import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.instruction.TickingInstruction;
import com.simibubi.create.foundation.utility.animation.LerpedFloat.Chaser;

public class CameraShakeInstruction extends TickingInstruction {

    public CameraShakeInstruction() {
        super(false, 10);
    };

    @Override
    public void tick(PonderScene scene) {
        super.tick(scene);
        scene.getTransform().xRotation.chase(20f * (remainingTicks % 2 == 0 ? 1f : -1f) * Math.exp(- remainingTicks), 1f, Chaser.EXP);
    };
    
};
