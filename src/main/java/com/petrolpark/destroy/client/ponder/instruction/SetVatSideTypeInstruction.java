package com.petrolpark.destroy.client.ponder.instruction;

import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity.DisplayType;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;

import net.minecraft.core.BlockPos;

public class SetVatSideTypeInstruction extends PonderInstruction {

    public final BlockPos blockPos;
    public final VatSideBlockEntity.DisplayType displayType;

    public SetVatSideTypeInstruction(BlockPos blockPos, DisplayType displayType) {
        this.blockPos = blockPos;
        this.displayType = displayType;
    };

    @Override
    public boolean isComplete() {
        return true;
    };

    @Override
    public void tick(PonderScene scene) {
        if (scene.getWorld().getBlockEntity(blockPos) instanceof VatSideBlockEntity vatSide)
            vatSide.setDisplayType(displayType);
    };
    
};
