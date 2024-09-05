package com.petrolpark.destroy.util;

import com.petrolpark.destroy.network.packet.SmartExplosionS2CPacket;
import com.petrolpark.destroy.world.explosion.SmartExplosion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.ForgeEventFactory;

public class ExplosionHelper {

    public static Explosion explode(ServerLevel level, SmartExplosion explosion) {
        if (ForgeEventFactory.onExplosionStart(level, explosion)) return explosion; // True if cancelled
        explosion.explode();
        explosion.finalizeExplosion(true);

        for(ServerPlayer player : level.getPlayers(player -> player.distanceToSqr(explosion.getPosition()) < 4096d)) {
            SmartExplosionS2CPacket.send(player, explosion);
        };

        return explosion;
    };
};
