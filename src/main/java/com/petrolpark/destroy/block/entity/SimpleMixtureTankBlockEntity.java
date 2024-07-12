package com.petrolpark.destroy.block.entity;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import com.petrolpark.destroy.block.SimplePlaceableMixtureTankBlock;
import com.petrolpark.destroy.block.entity.behaviour.fluidTankBehaviour.GeniusFluidTankBehaviour;
import com.petrolpark.destroy.block.renderer.SimpleMixtureTankRenderer.ISimpleMixtureTankRenderInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.Couple;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;

public abstract class SimpleMixtureTankBlockEntity extends SmartBlockEntity implements ISimpleMixtureTankRenderInformation<Void> {

    protected GeniusFluidTankBehaviour tank;

    public SimpleMixtureTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = new GeniusFluidTankBehaviour(GeniusFluidTankBehaviour.TYPE, this, 1, 1000000, false);
        behaviours.add(tank);
    };

    @Override
    public FluidStack getRenderedFluid(Void foid) {
        return tank.getPrimaryTank().getRenderedFluid();
    };

    @Override
    public float getFluidLevel(Void foid, float partialTicks) {
        return tank.getPrimaryTank().getFluidLevel().getValue(partialTicks);
    };

    public GeniusFluidTankBehaviour getTank() {
        return tank;
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return tank.getCapability().cast();
        };
        return super.getCapability(cap, side);
    };

    public static class SimplePlaceableMixtureTankBlockEntity extends SimpleMixtureTankBlockEntity {

        private final SimplePlaceableMixtureTankBlock block;

        public SimplePlaceableMixtureTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state);
            if (!(state.getBlock() instanceof SimplePlaceableMixtureTankBlock block)) throw new IllegalArgumentException("This Block Entity can only apply to SimplePlaceableMixtureStorageBlocks");
            this.block = block;
            tank.setCapacity(block.getMixtureCapacity());
        };

        @Override
        public Couple<Vector3f> getFluidBoxDimensions() {
            return block.getFluidBoxDimensions();
        };

    };
    
};
