package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class RequestInventoryFullStateC2SPacket extends C2SPacket {

    @Override
    public void toBytes(FriendlyByteBuf buffer) {};

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            supplier.get().getSender().inventoryMenu.broadcastFullState();
        });
        return true;
    };
    
};
