package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.block.entity.VatSideBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class VatSideQuantityThresholdChangeC2SPacket extends C2SPacket {

    public final boolean upper;
    public final float value;
    public final BlockPos pos;

    public VatSideQuantityThresholdChangeC2SPacket(boolean upper, float value, BlockPos pos) {
        this.upper = upper;
        this.value = value;
        this.pos = pos;
    };

    public VatSideQuantityThresholdChangeC2SPacket(FriendlyByteBuf buffer) {
        upper = buffer.readBoolean();
        value = buffer.readFloat();
        pos = buffer.readBlockPos();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(upper);
        buffer.writeFloat(value);
        buffer.writeBlockPos(pos);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            BlockEntity be = sender.level().getBlockEntity(pos);
            if (be != null && be instanceof VatSideBlockEntity vbe) {
                if (upper) vbe.redstoneMonitor.upperThreshold = value;
                else vbe.redstoneMonitor.lowerThreshold = value;
                vbe.notifyUpdate();
            };
        });
        return true;
    };
    
};
