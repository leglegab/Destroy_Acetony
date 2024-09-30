package com.petrolpark.destroy.util;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;

public class ItemHelper {
    
    /**
     * Copies an Item in an ItemStack a given number of times.
     * If the number exceeds the maximum stack size of the Item, it is split into multiple Item Stacks.
     */
    public static Collection<ItemStack> withCount(ItemStack stack, int count) {
        int maxStackSize = stack.getMaxStackSize();
        List<ItemStack> stacks = new ArrayList<>(1 + (count / maxStackSize));
        for (int i = 0; i < count / maxStackSize; i++) stacks.add(stack.copyWithCount(maxStackSize));
        stacks.add(stack.copyWithCount(count % maxStackSize));
        return stacks;
    };
};
