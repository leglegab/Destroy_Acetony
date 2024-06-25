package com.petrolpark.destroy.item.inventory;

import org.jetbrains.annotations.NotNull;

import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosiveProperty;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CustomExplosiveMixInventory extends ItemStackHandler {
    
    public CustomExplosiveMixInventory(int size) {
        super(size);
    };

    public static boolean canBeAdded(ItemStack stack) {
        return ExplosiveProperties.ITEM_EXPLOSIVE_PROPERTIES.get(stack.getItem()) != null; // Must have explosive properties
    };

    public ExplosiveProperties getExplosiveProperties() {
        ExplosiveProperties properties = new ExplosiveProperties();
        for (int slot = 0; slot < getSlots(); slot++) {
            ItemStack stack = getStackInSlot(slot);
            ExplosiveProperties itemProperties = ExplosiveProperties.ITEM_EXPLOSIVE_PROPERTIES.getOrDefault(stack.getItem(), new ExplosiveProperties());
            for (ExplosiveProperty property : ExplosiveProperty.values()) properties.merge(property, itemProperties.get(property), Float::sum);
        };
        properties.replaceAll((ep, v) -> Mth.clamp(v, -10f, 10f));
        return properties;
    };

    @Override
    public final boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return canBeAdded(stack);
    };

    @Override
    public final int getSlotLimit(int slot) {
        return 1;
    };
};
