package com.petrolpark.destroy.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class DrinkItem extends Item {

    protected static final int DRINK_DURATION = 40;

    public DrinkItem(Properties properties) {
        super(properties);
    };

    public int getUseDuration(ItemStack stack) {
        return DRINK_DURATION;
    };
    
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public SoundEvent getEatingSound() {
        return getDrinkingSound();
    };

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    };
    
};
