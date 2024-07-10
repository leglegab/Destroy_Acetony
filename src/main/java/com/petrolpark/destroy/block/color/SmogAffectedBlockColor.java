package com.petrolpark.destroy.block.color;

import java.util.function.BiFunction;
import javax.annotation.Nullable;

import com.petrolpark.destroy.capability.Pollution;
import com.petrolpark.destroy.capability.Pollution.PollutionType;
import com.simibubi.create.foundation.ponder.PonderWorld;
import com.simibubi.create.foundation.utility.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

public class SmogAffectedBlockColor implements BlockColor {

    public static final ColorResolver GRASS_COLOR_RESOLVER = (b, x, z) -> -1;
    public static final ColorResolver FOLIAGE_COLOR_RESOLVER = (b, x, z) -> -1;
    public static final ColorResolver WATER_COLOR_RESOLVER = (b, x, z) -> -1;

    public static final int getAverageGrassColor(BlockAndTintGetter level, BlockPos pos) {
        return level.getBlockTint(pos, GRASS_COLOR_RESOLVER);
    };

    public static final int getAverageFoliageColor(BlockAndTintGetter level, BlockPos pos) {
        return level.getBlockTint(pos, FOLIAGE_COLOR_RESOLVER);
    };

    public static final int getAverageWaterColor(BlockAndTintGetter level, BlockPos pos) {
        return level.getBlockTint(pos, WATER_COLOR_RESOLVER);
    };

    public static final BlockColor

    GRASS = (state, level, pos, tintIndex) ->  level != null && pos != null ? SmogAffectedBlockColor.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor(),
    DOUBLE_TALL_GRASS = (state, level, pos, tintIndex) -> level != null && pos != null ? SmogAffectedBlockColor.getAverageGrassColor(level, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos) : GrassColor.getDefaultColor(),
    PINK_PETALS = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageGrassColor, GrassColor.getDefaultColor()),
    FOLIAGE = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageFoliageColor, FoliageColor.getDefaultColor()),
    BIRCH = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageFoliageColor, FoliageColor.getBirchColor()),
    SPRUCE = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageFoliageColor, FoliageColor.getEvergreenColor()),
    WATER = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageWaterColor, -1),
    SUGAR_CANE = new SmogAffectedBlockColor(SmogAffectedBlockColor::getAverageGrassColor, -1);

    private final BlockColor wrapped;

    public SmogAffectedBlockColor(BiFunction<BlockAndTintGetter, BlockPos, Integer> levelAndPosBlockColor, int fallback) {
        this((state, level, pos, tintIndex) -> level != null && pos != null ? levelAndPosBlockColor.apply(level, pos) : fallback);
    };

    public SmogAffectedBlockColor(BlockColor wrapped) {
        this.wrapped = wrapped;
    };

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        return wrapped.getColor(state, level, pos, tintIndex);
    };

    /**
     * Get the color of a Block due to Smog, not accounting for the colors of any Blocks surrounding it.
     */
    public static int getColor(int originalColor, @Nullable BlockPos pos, BlockAndTintGetter level) {
        LazyOptional<Pollution> pollutionOp;
        if (level instanceof PonderWorld ponder) {
            pollutionOp = ponder.getCapability(Pollution.CAPABILITY);
        } else if (pos != null) {
            Minecraft mc = Minecraft.getInstance();
            ChunkPos chunkPos = new ChunkPos(pos); 
            LevelChunk chunk = mc.level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, false);
            if (chunk != null) {
                pollutionOp = chunk.getCapability(Pollution.CAPABILITY);
            } else {
                return originalColor;
            };
        } else {
            return originalColor;
        };
        if (!pollutionOp.isPresent()) return originalColor;
        return Color.mixColors(originalColor, brown, (float) pollutionOp.resolve().get().get(PollutionType.SMOG) / PollutionType.SMOG.max);
    };
    
    private static final int brown = 0x382515;
    
};

