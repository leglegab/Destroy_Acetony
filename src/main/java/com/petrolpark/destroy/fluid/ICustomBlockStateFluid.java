package com.petrolpark.destroy.fluid;

import com.petrolpark.destroy.MoveToPetrolparkLibrary;

import net.minecraft.world.level.block.state.BlockState;

@MoveToPetrolparkLibrary
public interface ICustomBlockStateFluid {
    
    /**
     * @return The BlockState corresponding to 1000mB of this Fluid
     */
    public BlockState getBlockState();
};
