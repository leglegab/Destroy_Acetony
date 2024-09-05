package com.petrolpark.destroy.compat.createbigcannons.block.entity;

import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.block.entity.SimpleDyeableNameableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomExplosiveMixChargeBlockEntity extends SimpleDyeableNameableCustomExplosiveMixBlockEntity {

    public static ExplosivePropertyCondition[] EXPLOSIVE_PROPERTY_CONDITIONS = new ExplosivePropertyCondition[]{
        ExplosiveProperties.CAN_EXPLODE
    };
    
    public CustomExplosiveMixChargeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public CustomExplosiveMixInventory createInv() {
        return new CustomExplosiveMixInventory(DestroyAllConfigs.SERVER.compat.customExplosiveMixChargeSize.get());
    };

    @Override
    public void explode(@Nullable Player cause) {
        
    };

    @Override
    public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
        return EXPLOSIVE_PROPERTY_CONDITIONS;
    };
    
};