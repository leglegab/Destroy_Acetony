package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.entity.player.ExtendedInventory;
import com.petrolpark.destroy.entity.player.ExtendedInventoryClientHandler;
import com.petrolpark.destroy.network.DestroyMessages;

import net.minecraft.client.Minecraft;
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
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            ExtendedInventory inv = ExtendedInventory.get(mc.player);
            inv.setExtraInventorySize(extraInventorySize);
            inv.setExtraHotbarSlots(extraHotbarSlots);
            ExtendedInventoryClientHandler.refreshClientInventoryMenu(inv);
            if (requestFullState) DestroyMessages.sendToServer(new RequestInventoryFullStateC2SPacket());
        });
        return true;
    };
    
};
