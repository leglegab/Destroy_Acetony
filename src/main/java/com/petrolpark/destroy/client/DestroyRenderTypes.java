package com.petrolpark.destroy.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.petrolpark.destroy.Destroy;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class DestroyRenderTypes extends RenderStateShard {

    private static final RenderType FLUID_NO_CULL = RenderType.create(Destroy.asResource("fluid_no_cull").toString(),
    DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
        .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
        .setTextureState(BLOCK_SHEET_MIPPED)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLightmapState(LIGHTMAP)
        .setOverlayState(OVERLAY)
        .createCompositeState(true));

    public static RenderType fluidNoCull() {
        return FLUID_NO_CULL;
    };
    
    public DestroyRenderTypes() {
        super(null, null, null);
    };
};
