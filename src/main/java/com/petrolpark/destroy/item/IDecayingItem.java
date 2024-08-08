package com.petrolpark.destroy.item;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.MoveToPetrolparkLibrary;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

@MoveToPetrolparkLibrary
public interface IDecayingItem {

    /**
     * Get the Item Stack into which the decaying Item Stack decays.
     * @param stack
     */
    public ItemStack getDecayProduct(ItemStack stack);

    /**
     * Get the total lifetime in ticks of an Item Stack, not considering the current time it has been alive.
     * @param stack
     */
    public long getLifetime(ItemStack stack);

    public default boolean areDecayTimesCombineable(ItemStack stack1, ItemStack stack2) {
        return true;
    };

    public default String getDecayTimeTranslationKey(ItemStack stack) {
        return "item.destroy.decaying_item.remaining";
    };

    public static ItemStack checkDecay(ItemStack stack) {
        if (stack.isEmpty()) return stack;
        if (stack.getItem() instanceof IDecayingItem item) {
            CompoundTag tag = stack.getOrCreateTag();
            long gameTime = Destroy.DECAYING_ITEM_HANDLER.get().getGameTime();
            if (tag.contains("CreationTime", Tag.TAG_LONG)) {
                long timeDead = gameTime - tag.getLong("CreationTime") - item.getLifetime(stack);
                if (timeDead >= 0) {
                    ItemStack product = item.getDecayProduct(stack);
                    startDecay(product, timeDead);
                    return product;
                };
            };
        };
        return stack;
    };

    public static long getRemainingTime(IDecayingItem decayingItem, ItemStack decayingItemStack, CompoundTag decayingItemTag) {
        return decayingItem.getLifetime(decayingItemStack) + decayingItemTag.getLong("CreationTime") - Destroy.DECAYING_ITEM_HANDLER.get().getGameTime();
    };

    public static void startDecay(ItemStack stack, long timeElapsed) {
        if (stack.getItem() instanceof IDecayingItem item) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains("CreationTime", Tag.TAG_LONG)) tag.putLong("CreationTime", Destroy.DECAYING_ITEM_HANDLER.get().getGameTime() - timeElapsed);
        };
    };
    
};
