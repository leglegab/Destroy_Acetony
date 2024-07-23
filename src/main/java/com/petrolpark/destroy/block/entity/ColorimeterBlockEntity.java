package com.petrolpark.destroy.block.entity;

import java.util.List;
import java.util.Optional;

import com.petrolpark.destroy.block.ColorimeterBlock;
import com.petrolpark.destroy.block.entity.behaviour.RedstoneQuantityMonitorBehaviour;
import com.petrolpark.destroy.chemistry.Molecule;
import com.petrolpark.destroy.chemistry.ReadOnlyMixture;
import com.petrolpark.destroy.fluid.DestroyFluids;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public class ColorimeterBlockEntity extends SmartBlockEntity {

    protected boolean observingGas;
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

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        setMolecule(Molecule.getMolecule(tag.getString("Molecule")));
        if (tag.contains("ObservingGas")) observingGas = true;
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (molecule != null) tag.putString("Molecule", molecule.getFullID());
        if (observingGas) tag.putBoolean("ObservingGas", true);
    };

    public void configure(Molecule observedMolecule, boolean observingGas) {
        this.observingGas = observingGas;
        setMolecule(observedMolecule);
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
                redstoneMonitor.quantityObserved = Optional.of(() -> {
                    FluidStack mixtureStack = (observingGas ? vbe.getController().getGasTankContents() : vbe.getController().getLiquidTankContents());
                    if (DestroyFluids.isMixture(mixtureStack)) {
                        ReadOnlyMixture mixture = ReadOnlyMixture.readNBT(ReadOnlyMixture::new, mixtureStack.getOrCreateChildTag("Mixture"));
                        return mixture.getConcentrationOf(molecule);
                    };
                    return 0f;
                });
                return;
            };
        };
        redstoneMonitor.quantityObserved = Optional.empty();
    };
    
};
