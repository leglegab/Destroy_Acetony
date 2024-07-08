package com.petrolpark.destroy.client.ponder.instruction;

import com.petrolpark.destroy.MoveToPetrolparkLibrary;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.instruction.TickingInstruction;

import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;

@MoveToPetrolparkLibrary
public class AdvanceTimeOfDayInstruction extends TickingInstruction {

    public final int speed;

    public AdvanceTimeOfDayInstruction(int ticks, int speed) {
        super(false, ticks);
        this.speed = speed;
    };

    @Override
    public void tick(PonderScene scene) {
        super.tick(scene);
        ClientLevelData levelData = (ClientLevelData)scene.getWorld().getLevelData();
        levelData.setDayTime(levelData.getDayTime() + speed);
    };
    
};
