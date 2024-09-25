package com.petrolpark.destroy.client.ponder.instruction;

import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.item.IMixtureStorageItem.SinglePhaseVatExtraction;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;

import net.minecraft.core.BlockPos;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class DrainVatInstruction extends PonderInstruction {
    
    public final BlockPos vatControllerPos;
    public final boolean liquid;
    public final int drainAmount;

    public DrainVatInstruction(BlockPos vatControllerPos, int drainAmount) {
        this(vatControllerPos, true, drainAmount);
    };

    public DrainVatInstruction(BlockPos vatControllerPos, boolean liquid, int drainAmount) {
        this.vatControllerPos = vatControllerPos;
        this.liquid = liquid;
        this.drainAmount = drainAmount;
    };

    @Override
    public boolean isComplete() {
        return true;
    };

    @Override
    public void tick(PonderScene scene) {
        scene.getWorld().getBlockEntity(vatControllerPos, DestroyBlockEntityTypes.VAT_CONTROLLER.get()).ifPresent(vat -> {
            new SinglePhaseVatExtraction(vat, !liquid).drain(drainAmount, FluidAction.EXECUTE);
        });
    };
};
