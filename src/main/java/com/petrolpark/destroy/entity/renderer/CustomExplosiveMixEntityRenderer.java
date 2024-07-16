package com.petrolpark.destroy.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.petrolpark.destroy.block.model.DestroyPartials;
import com.petrolpark.destroy.entity.CustomExplosiveMixEntity;
import com.simibubi.create.foundation.render.CachedBufferer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class CustomExplosiveMixEntityRenderer extends PrimedBombRenderer<CustomExplosiveMixEntity> {

    public CustomExplosiveMixEntityRenderer(Context context) {
        super(context);
    };

    @Override
    public void renderBlock(CustomExplosiveMixEntity entity, PoseStack ms, MultiBufferSource buffer, int light, int fuse) {
        BlockState state = entity.getBlockStateToRender();
        VertexConsumer vc = buffer.getBuffer(RenderType.cutout());
        CachedBufferer.partial(DestroyPartials.CUSTOM_EXPLOSIVE_MIX_BASE, state)
            .light(light)
            .color(entity.color)
            .renderInto(ms, vc);
        CachedBufferer.partial(DestroyPartials.CUSTOM_EXPLOSIVE_MIX_OVERLAY, state)
            .light(light)
            .renderInto(ms, vc);
    };
    
};
