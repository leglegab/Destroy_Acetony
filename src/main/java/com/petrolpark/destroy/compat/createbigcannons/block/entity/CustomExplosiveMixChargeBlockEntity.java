package com.petrolpark.destroy.compat.createbigcannons.block.entity;

import com.petrolpark.destroy.block.entity.SimpleDyeableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomExplosiveMixChargeBlockEntity extends SimpleDyeableCustomExplosiveMixBlockEntity {

    public CustomExplosiveMixChargeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public CustomExplosiveMixInventory createInv() {
        return new CustomExplosiveMixInventory(DestroyAllConfigs.SERVER.compat.customExplosiveMixChargeSize.get());
    };
    
};