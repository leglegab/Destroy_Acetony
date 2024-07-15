package com.petrolpark.destroy.item;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;

import net.minecraft.world.level.block.Block;

public class CustomExplosiveMixBlockItem extends DyeableCustomExplosiveMixBlockItem {

    public CustomExplosiveMixBlockItem(Block block, Properties properties) {
        super(block, properties);
    };

    @Override
    public int getExplosiveInventorySize() {
        return DestroyAllConfigs.SERVER.blocks.customExplosiveMixSize.get();
    };

    @Override
    public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
        return new ExplosivePropertyCondition[0]; //TODO conditions for regular explosive
    };
    
};
