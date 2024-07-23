package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.chemistry.Molecule;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ConfigureColorimeterC2SPacket extends C2SPacket {

    protected final boolean observingGas;
    protected final Molecule molecule;
    protected final BlockPos pos;

    public ConfigureColorimeterC2SPacket(FriendlyByteBuf buffer) {
        observingGas = buffer.readBoolean();
        if (buffer.readBoolean()) {
            molecule = Molecule.getMolecule(buffer.readUtf());
        } else {
            molecule = null;
        }
        pos = buffer.readBlockPos();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(observingGas);
        buffer.writeBoolean(molecule != null);
        if (molecule != null) buffer.writeUtf(molecule.getFullID());
        buffer.writeBlockPos(pos);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        Context context = supplier.get();
        context.enqueueWork(() -> {
            context.getSender().level().getBlockEntity(pos, DestroyBlockEntityTypes.COLORIMETER.get()).ifPresent(be -> {
                be.configure(molecule, observingGas);
            });
        });
        return true;
    };
    
};
