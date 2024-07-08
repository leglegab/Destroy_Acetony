package com.petrolpark.destroy.block.color;

import java.util.function.BiFunction;
import javax.annotation.Nullable;

import com.petrolpark.destroy.capability.level.pollution.ClientLevelPollutionData;
import com.petrolpark.destroy.capability.level.pollution.LevelPollution;
import com.petrolpark.destroy.capability.level.pollution.LevelPollutionProvider;
import com.petrolpark.destroy.capability.level.pollution.LevelPollution.PollutionType;
import com.simibubi.create.foundation.ponder.PonderWorld;
import com.simibubi.create.foundation.utility.Color;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.common.util.LazyOptional;

public class SmogAffectedBlockColor implements BlockColor {

    public static final SmogAffectedBlockColor
    GRASS = new SmogAffectedBlockColor((state, level, pos, tintIndex) ->  level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor()),
    DOUBLE_TALL_GRASS = new SmogAffectedBlockColor((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos) : GrassColor.getDefaultColor()),
    PINK_PETALS = new SmogAffectedBlockColor(BiomeColors::getAverageGrassColor, GrassColor.getDefaultColor()),
    FOLIAGE = new SmogAffectedBlockColor(BiomeColors::getAverageFoliageColor, FoliageColor.getDefaultColor()),
    BIRCH = new SmogAffectedBlockColor(BiomeColors::getAverageFoliageColor, FoliageColor.getBirchColor()),
    SPRUCE = new SmogAffectedBlockColor(BiomeColors::getAverageFoliageColor, FoliageColor.getEvergreenColor()),
    WATER = new SmogAffectedBlockColor(BiomeColors::getAverageWaterColor, -1),
    SUGAR_CANE = new SmogAffectedBlockColor(BiomeColors::getAverageGrassColor, -1);

    private final BlockColor originalColor;

    public SmogAffectedBlockColor(BiFunction<BlockAndTintGetter, BlockPos, Integer> levelAndPosBlockColor, int fallback) {
        this((state, level, pos, tintIndex) -> level != null && pos != null ? levelAndPosBlockColor.apply(level, pos) : fallback);
    };

    public SmogAffectedBlockColor(BlockColor originalColor) {
        this.originalColor = originalColor;
    };

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        int color = originalColor.getColor(state, level, pos, tintIndex);
        if (level == null || pos == null) return color;
        return getColor(color, level);
    };

    public static int getColor(int originalColor, BlockAndTintGetter level) {
        LevelPollution pollution = ClientLevelPollutionData.getLevelPollution();
        if (level instanceof PonderWorld ponder) {
            LazyOptional<LevelPollution> pollutionOp = ponder.getCapability(LevelPollutionProvider.LEVEL_POLLUTION);
            if (pollutionOp.isPresent()) pollution = pollutionOp.resolve().get();
        };
        if (pollution == null) return originalColor;
        return Color.mixColors(originalColor, brown, (float) pollution.get(PollutionType.SMOG) / PollutionType.SMOG.max);
    };
    
    private static final int brown = 0x382515;
    
};

