package com.petrolpark.destroy.item;

import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;

import net.minecraft.world.item.ItemStack;

public interface ICustomExplosiveMixItem {
    
    public default CustomExplosiveMixInventory getExplosiveInventory(ItemStack stack) {
        if (!stack.getItem().equals(this)) return new CustomExplosiveMixInventory(getExplosiveInventorySize());
        CustomExplosiveMixInventory inventory = new CustomExplosiveMixInventory(getExplosiveInventorySize());
        inventory.deserializeNBT(stack.getOrCreateTag().getCompound("ExplosiveMix"));
        return inventory;
    };

    public default void setExplosiveInventory(ItemStack stack, CustomExplosiveMixInventory inv) {
        if (inv != null && stack.getItem().equals(this)) stack.getOrCreateTag().put("ExplosiveMix", inv.serializeNBT());
    };

    public int getExplosiveInventorySize();

    public ExplosivePropertyCondition[] getApplicableExplosionConditions();
};
