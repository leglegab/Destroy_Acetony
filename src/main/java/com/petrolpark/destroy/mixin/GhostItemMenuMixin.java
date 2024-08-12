package com.petrolpark.destroy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import com.simibubi.create.foundation.gui.menu.MenuBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

@Mixin(GhostItemMenu.class)
public abstract class GhostItemMenuMixin extends MenuBase<Object> {

    protected GhostItemMenuMixin(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
        throw new AssertionError();
    };
    
    @Inject(
        method = "Lcom/simibubi/create/foundation/gui/menu/GhostItemMenu;clicked",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    public void inClicked(int slotId, int dragType, ClickType clickTypeIn, Player player, CallbackInfo ci) {
        if (slots.get(slotId).container == player.getInventory()) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            ci.cancel();
        };
    };

    @Inject(
        method = "Lcom/simibubi/create/foundation/gui/menu/GhostItemMenu;quickMoveStack",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    public void inQuickMoveStack(Player playerIn, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (slots.get(index).container == player.getInventory() && index >= 36) {
            cir.setReturnValue(ItemStack.EMPTY);
        };
    };
};
