package com.petrolpark.destroy.item;

import com.petrolpark.destroy.world.explosion.SmartExplosion;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WaterSensitiveSpontaneouslyCombustingItem extends SpontaneouslyCombustingItem {

    public WaterSensitiveSpontaneouslyCombustingItem(Properties properties) {
        super(properties);
    };
    
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        checkForWater(stack, entity, isSelected);
    };

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        checkForWater(stack, entity, true);
        return super.onEntityItemUpdate(stack, entity);
    };

    public void checkForWater(ItemStack stack, Entity entity, boolean rainSensitive) {
        if (entity.isInWaterOrBubble() || (entity.isInWaterRainOrBubble() && (rainSensitive || (entity instanceof LivingEntity livingEntity && livingEntity.getOffhandItem() == stack)))) {
            SmartExplosion.explode(entity.level(), new SmartExplosion(entity.level(), entity, null, null, entity.position(), 2f, 0.7f));
        };
    };
};
