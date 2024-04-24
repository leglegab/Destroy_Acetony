package com.petrolpark.destroy.capability.level.pollution;

import com.petrolpark.destroy.capability.level.pollution.LevelPollution.PollutionType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.RenderChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientLevelPollutionData {
    private static LevelPollution levelPollution;

    private static Integer lastRenderedSmogLevel = null;

    public static void setLevelPollution(LevelPollution levelPollution) {
        ClientLevelPollutionData.levelPollution = levelPollution;
        if (lastRenderedSmogLevel == null || Math.abs(lastRenderedSmogLevel - levelPollution.get(PollutionType.SMOG)) >= 1000) {
            Minecraft mc = Minecraft.getInstance();
            for (RenderChunk chunk : mc.levelRenderer.viewArea.chunks) chunk.setDirty(false); // Re-render all chunks
            lastRenderedSmogLevel = levelPollution.get(PollutionType.SMOG);
        };
    };

    public static LevelPollution getLevelPollution() {
        return levelPollution;
    };
}
