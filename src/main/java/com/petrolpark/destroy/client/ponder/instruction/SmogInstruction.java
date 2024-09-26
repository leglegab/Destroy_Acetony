package com.petrolpark.destroy.client.ponder.instruction;

import com.petrolpark.destroy.capability.Pollution;
import com.petrolpark.destroy.capability.Pollution.PollutionType;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;

import net.minecraft.util.Mth;

public class SmogInstruction extends PonderInstruction {

    public final int value;

    public SmogInstruction(int value) {
        this.value = Mth.clamp(value, 0, PollutionType.SMOG.max);
    };

    @Override
    public boolean isComplete() {
        return true;
    };

    @Override
    public void tick(PonderScene scene) {
        scene.getWorld().getCapability(Pollution.CAPABILITY).ifPresent(pollution -> pollution.set(PollutionType.SMOG, value));
        scene.forEach(WorldSectionElement.class, e -> {
            e.queueRedraw();
        });
    };
    
};
