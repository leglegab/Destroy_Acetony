package com.petrolpark.destroy.fluid;

import com.petrolpark.destroy.block.DestroyBlocks;
import com.simibubi.create.content.fluids.VirtualFluid;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class MoltenStainlessSteelFluid extends VirtualFluid {

    public MoltenStainlessSteelFluid(Properties properties) {
        super(properties);
    };

    @Override
    protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL); // Not used
    };

    @Override
    public Fluid getSource() {
        return this;
    };

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return DestroyBlocks.MOLTEN_STAINLESS_STEEL.getDefaultState();
    };

    @Override
    public boolean isSource(FluidState state) {
        return true;
    };
    
};
