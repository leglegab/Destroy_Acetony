package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.entity.player.ExtendedInventoryClientHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ExtraInventorySizeChangeS2CPacket extends S2CPacket {

    public final int extraInventorySize;
    public final int extraHotbarSlots;
    public final boolean requestFullState;

    public ExtraInventorySizeChangeS2CPacket(int extraInventorySize, int extraHotbarSlots, boolean requestFullState) {
        this.extraInventorySize = extraInventorySize;
        this.extraHotbarSlots = extraHotbarSlots;
        this.requestFullState = requestFullState;
    };

    public ExtraInventorySizeChangeS2CPacket(FriendlyByteBuf buffer) {
        this.extraInventorySize = buffer.readVarInt();
        this.extraHotbarSlots = buffer.readVarInt();
        this.requestFullState = buffer.readBoolean();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(extraInventorySize);
        buffer.writeVarInt(extraHotbarSlots);
        buffer.writeBoolean(requestFullState);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> ExtendedInventoryClientHandler.handleExtendedInventorySizeChange(this));
        return true;
    };
    
};
