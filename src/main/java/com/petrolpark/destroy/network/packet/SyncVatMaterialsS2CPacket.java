package com.petrolpark.destroy.network.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.petrolpark.destroy.client.ponder.DestroyPonderTags;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class SyncVatMaterialsS2CPacket extends S2CPacket {

    private final Map<Block, VatMaterial> materials;

    public SyncVatMaterialsS2CPacket(Map<Block, VatMaterial> materials) {
        this.materials = materials;
    };

    public SyncVatMaterialsS2CPacket(FriendlyByteBuf buffer) {
        int count = buffer.readVarInt();
        materials = new HashMap<>(count);
        for (int i = 0; i < count; i++) {
            materials.put(ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation()), new VatMaterial(buffer.readFloat(), buffer.readFloat(), buffer.readBoolean(), false));
        };
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(materials.size());
        materials.forEach((block, material) -> {
            buffer.writeResourceLocation(RegisteredObjects.getKeyOrThrow(block));
            buffer.writeFloat(material.maxPressure());
            buffer.writeFloat(material.thermalConductivity());
            buffer.writeBoolean(material.transparent());
        });
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            VatMaterial.clearDatapackMaterials();
            VatMaterial.BLOCK_MATERIALS.putAll(materials);
            DestroyPonderTags.refreshVatMaterialsTag();
        });
        return true;
    };
    
};
