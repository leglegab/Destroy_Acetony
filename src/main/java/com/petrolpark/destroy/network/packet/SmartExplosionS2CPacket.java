package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.world.explosion.SmartExplosion;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;

public class SmartExplosionS2CPacket extends S2CPacket {

    public final SmartExplosion explosion;
    public final Vec3 recipientKnockback;

    protected SmartExplosionS2CPacket(SmartExplosion explosion, Vec3 recipientKnockback) {
        this.explosion = explosion;
        this.recipientKnockback = recipientKnockback;
    };

    public static final SmartExplosionS2CPacket read(FriendlyByteBuf buffer) {
        SmartExplosion.Serializer<?> serializer = SmartExplosion.getType(buffer.readResourceLocation());
        return new SmartExplosionS2CPacket(serializer.read(buffer), new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    };

    public static void send(ServerPlayer player, SmartExplosion explosion) {
        DestroyMessages.sendToClient(new SmartExplosionS2CPacket(explosion, explosion.getHitPlayers().get(player)), player);
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(explosion.getDefaultSerializer().id);
        explosion.write(buffer);
        buffer.writeDouble(recipientKnockback.x());
        buffer.writeDouble(recipientKnockback.y());
        buffer.writeDouble(recipientKnockback.z());
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            explosion.finalizeExplosion(true);
            mc.player.setDeltaMovement(recipientKnockback);
        });
        return true;
    };
    
};
