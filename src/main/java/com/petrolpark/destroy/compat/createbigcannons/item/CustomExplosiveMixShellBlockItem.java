package com.petrolpark.destroy.compat.createbigcannons.item;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.DyeableCustomExplosiveMixBlockItem;

import net.minecraft.world.level.block.Block;

public class CustomExplosiveMixShellBlockItem extends DyeableCustomExplosiveMixBlockItem {

    public CustomExplosiveMixShellBlockItem(Block block, Properties properties) {
        super(block, properties);
    };

    @Override
    public int getExplosiveInventorySize() {
        return DestroyAllConfigs.SERVER.compat.customExplosiveMixShellSize.get();
    };
    
};
