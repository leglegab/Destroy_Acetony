package com.petrolpark.destroy.block.entity;

import java.util.List;
import java.util.Optional;

import com.petrolpark.destroy.block.ColorimeterBlock;
import com.petrolpark.destroy.block.entity.behaviour.RedstoneQuantityMonitorBehaviour;
import com.petrolpark.destroy.chemistry.Molecule;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorimeterBlockEntity extends SmartBlockEntity {

    protected Molecule molecule;
    public RedstoneQuantityMonitorBehaviour redstoneMonitor;

    public ColorimeterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        redstoneMonitor = new RedstoneQuantityMonitorBehaviour(this);
        behaviours.add(redstoneMonitor);
    };

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
        updateVat();
    };

    public void updateVat() {
        BlockPos vatPos = getBlockPos().relative(getBlockState().getValue(ColorimeterBlock.FACING));
        BlockEntity be = getLevel().getBlockEntity(vatPos);
        if (molecule != null && be instanceof VatSideBlockEntity vbe) {
            if (vbe.getController() != null && VatMaterial.getMaterial(vbe.getMaterial()).map(VatMaterial::transparent).orElse(false)) {
                redstoneMonitor.quantityObserved = Optional.of(() -> 
                    vbe.getController().cachedMixture.getConcentrationOf(molecule)
                );
                return;
            };
        };
        redstoneMonitor.quantityObserved = Optional.empty();
    };
    
};
