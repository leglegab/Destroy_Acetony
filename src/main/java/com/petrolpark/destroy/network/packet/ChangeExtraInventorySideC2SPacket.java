package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.entity.player.ExtendedInventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ChangeExtraInventorySideC2SPacket extends C2SPacket {

    public final boolean left;

    public ChangeExtraInventorySideC2SPacket(boolean left) {
        this.left = left;
    };

    public ChangeExtraInventorySideC2SPacket(FriendlyByteBuf buffer) {
        left = buffer.readBoolean();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(left);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        Context context = supplier.get();
        context.enqueueWork(() -> {
            ExtendedInventory.get(context.getSender()).extraHotbarLeft = left;
        });
        return true;
    };
    
};
