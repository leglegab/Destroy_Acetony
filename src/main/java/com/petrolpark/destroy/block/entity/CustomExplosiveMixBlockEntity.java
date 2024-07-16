package com.petrolpark.destroy.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomExplosiveMixBlockEntity extends SimpleDyeableNameableCustomExplosiveMixBlockEntity {

    public static ExplosivePropertyCondition[] EXPLOSIVE_PROPERTY_CONDITIONS = new ExplosivePropertyCondition[]{ExplosiveProperties.CAN_EXPLODE, ExplosiveProperties.EVAPORATES_FLUIDS, ExplosiveProperties.EXPLODES_RANDOMLY, ExplosiveProperties.OBLITERATES, ExplosiveProperties.SILK_TOUCH};

    public CustomExplosiveMixBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
    };

    public void setCustomName(Component name) {
        this.name = name;
    };

    @Override
    public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
        return EXPLOSIVE_PROPERTY_CONDITIONS;
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
