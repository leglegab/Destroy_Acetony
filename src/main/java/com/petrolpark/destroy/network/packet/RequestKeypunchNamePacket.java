package com.petrolpark.destroy.network.packet;

import java.util.List;
import java.util.function.Supplier;

import com.petrolpark.destroy.block.KeypunchBlock;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.util.NameLists;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class RequestKeypunchNamePacket extends S2CPacket {

    public final BlockPos pos;

    public RequestKeypunchNamePacket(BlockPos pos) {
        this.pos = pos;
    };

    public RequestKeypunchNamePacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            List<String> names = NameLists.getNames(KeypunchBlock.NAME_LIST_ID);
            DestroyMessages.sendToServer(new NameKeypunchC2SPacket(pos, names.get(minecraft.level.getRandom().nextInt(names.size()))));
        });
        return true;
    };
    
};
