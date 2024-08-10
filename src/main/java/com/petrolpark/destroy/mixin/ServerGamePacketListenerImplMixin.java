package com.petrolpark.destroy.mixin;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.petrolpark.destroy.entity.player.ExtendedInventory;

import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin implements ServerGamePacketListener {

    @Shadow
    public ServerPlayer player;

    @Shadow
    static Logger LOGGER;
    
    @Overwrite
    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet) {
        PacketUtils.ensureRunningOnSameThread(packet, this, player.serverLevel());
        ExtendedInventory inv = ExtendedInventory.get(player);
        if (inv.isExtendedHotbarSlot(packet.getSlot())) {
            if (player.getInventory().selected != packet.getSlot() && player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                player.stopUsingItem();
            };

            inv.selected = packet.getSlot();
            player.resetLastActionTime();
        } else {
            LOGGER.warn("{} tried to set an invalid carried item", player.getName().getString());
        };
    };
};
