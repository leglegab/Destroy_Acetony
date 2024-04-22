package com.petrolpark.destroy.item.colors;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class DyeableCustomExplosiveMixItemColor implements ItemColor {

    public static final DyeableCustomExplosiveMixItemColor INSTANCE = new DyeableCustomExplosiveMixItemColor();

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex != 0) return -1;
        return ((DyeableLeatherItem)stack.getItem()).getColor(stack);
    };
    
};
