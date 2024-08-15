package com.petrolpark.destroy.mixin;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.petrolpark.destroy.entity.player.ExtendedInventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;


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

    @Inject(
        method = "handleSetCreativeModeSlot",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT,
        cancellable = true
    )
    public void inHandleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket packet, CallbackInfo ci, boolean flag, ItemStack itemstack, CompoundTag compoundtag, boolean flag1) {
        if (packet.getSlotNum() >= 1 && itemstack.isEmpty() || itemstack.getDamageValue() >= 0 && !itemstack.isEmpty()) {
            player.inventoryMenu.getSlot(packet.getSlotNum()).setByPlayer(itemstack);
            player.inventoryMenu.broadcastChanges();
            ci.cancel();
        };
    };
};
