package com.petrolpark.destroy.item.food;

import java.util.function.Supplier;

import com.petrolpark.destroy.effect.DestroyMobEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class DestroyFoods {

    // Effect Suppliers
    public static Supplier<MobEffectInstance> babyBlueHigh = () -> {
        return new MobEffectInstance(DestroyMobEffects.BABY_BLUE_HIGH.get(), 2400, 0);
    };

    // Foods
    public static final FoodProperties
    APPLE_JUICE = new FoodProperties.Builder().nutrition(5).saturationMod(0.4f).effect(() -> new MobEffectInstance(DestroyMobEffects.FULL_BLADDER.get(), 600, 0, false, false, true), 1.0f).build(),
    BUTTER = new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build(),
    RAW_FRIES = new FoodProperties.Builder().nutrition(2).saturationMod(0.6f).build(),
    FRIES = new FoodProperties.Builder().nutrition(6).saturationMod(1.5f).build(),
    MASHED_POTATO = new FoodProperties.Builder().nutrition(5).saturationMod(1.4f).build(),
    MILK_CARTON = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).build(),
    CHEWING_GUM = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).build(),
    POTATE_O = new FoodProperties.Builder().nutrition(2).saturationMod(0.6f).build(),
    BIFURICATED_CARROT = new FoodProperties.Builder().nutrition(6).saturationMod(1.2f).build(),
    BABY_BLUE_POWDER = new FoodProperties.Builder().nutrition(0).saturationMod(0.0F).effect(babyBlueHigh, 1.0f).build(),
    MOONSHINE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).build(),
    BANGERS_AND_MASH = new FoodProperties.Builder().nutrition(8).saturationMod(1.8f).build();
}
