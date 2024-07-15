package com.petrolpark.destroy.block.entity;

import javax.annotation.Nullable;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomExplosiveMixBlockEntity extends SimpleDyeableNameableCustomExplosiveMixBlockEntity {

    public CustomExplosiveMixBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
        // TODO Auto-generated method stub
        return new ExplosivePropertyCondition[0];
    };

    @Override
    public CustomExplosiveMixInventory createInv() {
        return new CustomExplosiveMixInventory(DestroyAllConfigs.SERVER.blocks.customExplosiveMixSize.get());
    };

    @Nullable
    public Component getCustomName() {
        return name;
    };
    
};
