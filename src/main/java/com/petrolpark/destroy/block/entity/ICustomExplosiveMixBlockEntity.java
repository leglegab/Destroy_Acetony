package com.petrolpark.destroy.block.entity;

import com.petrolpark.destroy.item.ICustomExplosiveMixItem;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;

import net.minecraft.world.item.ItemStack;

/**
 * You must call {@link ICustomExplosiveMixBlockEntity#onPlace} when the block associated with this Block Entity gets placed,
 * and {@link ICustomExplosiveMixBlockEntity#getFilledItemStack} for pick-block.
 */
public interface ICustomExplosiveMixBlockEntity {

    public CustomExplosiveMixInventory getExplosiveInventory();

    public void setExplosiveInventory(CustomExplosiveMixInventory inv);
    
    public default void onPlace(ItemStack blockItemStack) {
        if (blockItemStack.getItem() instanceof ICustomExplosiveMixItem customMixItem) setExplosiveInventory(customMixItem.getExplosiveInventory(blockItemStack));
    };

    public default ItemStack getFilledItemStack(ItemStack emptyItemStack) {
        if (emptyItemStack.getItem() instanceof ICustomExplosiveMixItem customMixItem) customMixItem.setExplosiveInventory(emptyItemStack, getExplosiveInventory());
        return emptyItemStack;
    };
};
