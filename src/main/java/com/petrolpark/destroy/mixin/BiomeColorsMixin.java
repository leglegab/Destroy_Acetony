package com.petrolpark.destroy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.petrolpark.destroy.block.color.SmogAffectedBlockColor;
import com.petrolpark.destroy.config.DestroyAllConfigs;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {
    
    /**
     * Forge Fluids bypass the {@link net.minecraft.client.color.block.BlockColors usual approach} to tinting water.
     */
    @Overwrite
    public static int getAverageWaterColor(BlockAndTintGetter level, BlockPos pos) {
        return DestroyAllConfigs.SERVER.pollution.smog.get() ? SmogAffectedBlockColor.getColor(level.getBlockTint(pos, BiomeColors.WATER_COLOR_RESOLVER), level) : level.getBlockTint(pos, BiomeColors.WATER_COLOR_RESOLVER);
    };
};
