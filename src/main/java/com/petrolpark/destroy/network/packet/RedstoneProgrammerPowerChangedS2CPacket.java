package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.client.gui.menu.RedstoneProgrammerMenu.DummyRedstoneProgram;
import com.petrolpark.destroy.client.gui.screen.RedstoneProgrammerScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class RedstoneProgrammerPowerChangedS2CPacket extends S2CPacket {

    public final boolean powered;

    public RedstoneProgrammerPowerChangedS2CPacket(boolean powered) {
        this.powered = powered;
    };

    public RedstoneProgrammerPowerChangedS2CPacket(FriendlyByteBuf buffer) {
        powered = buffer.readBoolean();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(powered);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen instanceof RedstoneProgrammerScreen screen) {
                if (screen.program instanceof DummyRedstoneProgram program) program.powered = powered;
            };
        });
        return true;
    };
    
};
