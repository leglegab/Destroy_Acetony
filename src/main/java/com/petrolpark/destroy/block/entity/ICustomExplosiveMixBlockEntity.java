package com.petrolpark.destroy.block.entity;

import com.petrolpark.destroy.client.gui.menu.CustomExplosiveMenu;
import com.petrolpark.destroy.item.ICustomExplosiveMixItem;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

/**
 * You must call {@link ICustomExplosiveMixBlockEntity#onPlace} when the block associated with this Block Entity gets placed,
 * and {@link ICustomExplosiveMixBlockEntity#getFilledItemStack} for pick-block.
 */
public interface ICustomExplosiveMixBlockEntity extends MenuProvider {

    public CustomExplosiveMixInventory getExplosiveInventory();

    public void setExplosiveInventory(CustomExplosiveMixInventory inv);

    public ExplosivePropertyCondition[] getApplicableExplosionConditions();
    
    public default void onPlace(ItemStack blockItemStack) {
        if (blockItemStack.getItem() instanceof ICustomExplosiveMixItem customMixItem) setExplosiveInventory(customMixItem.getExplosiveInventory(blockItemStack));
    };

    public default ItemStack getFilledItemStack(ItemStack emptyItemStack) {
        if (emptyItemStack.getItem() instanceof ICustomExplosiveMixItem customMixItem) customMixItem.setExplosiveInventory(emptyItemStack, getExplosiveInventory());
        return emptyItemStack;
    };

    @Override
    public default AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return CustomExplosiveMenu.create(containerId, playerInventory, this);
    };

    public default void writeToBuffer(FriendlyByteBuf buffer) {
        buffer.writeComponent(getDisplayName());
        buffer.writeVarInt(getExplosiveInventory().getSlots());
        buffer.writeNbt(getExplosiveInventory().serializeNBT());
        buffer.writeVarInt(getApplicableExplosionConditions().length);
        for (ExplosivePropertyCondition c : getApplicableExplosionConditions()) buffer.writeResourceLocation(c.rl);
    };
};
