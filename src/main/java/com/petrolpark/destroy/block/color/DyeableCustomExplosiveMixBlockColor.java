package com.petrolpark.destroy.block.color;

import com.petrolpark.destroy.block.entity.IDyeableCustomExplosiveMixBlockEntity;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyeableCustomExplosiveMixBlockColor implements BlockColor {

    public static final DyeableCustomExplosiveMixBlockColor INSTANCE = new DyeableCustomExplosiveMixBlockColor();

    @Override
    public int getColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int tintIndex) {
        if (tintIndex != 0 || level == null || pos == null) return -1;
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof IDyeableCustomExplosiveMixBlockEntity dyeableBE)) return -1;
        return dyeableBE.getColor();
    };
    
};
