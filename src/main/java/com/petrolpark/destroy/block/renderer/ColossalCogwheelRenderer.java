package com.petrolpark.destroy.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.block.ColossalCogwheelBlock;
import com.petrolpark.destroy.block.entity.ColossalCogwheelBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ColossalCogwheelRenderer extends KineticBlockEntityRenderer<ColossalCogwheelBlockEntity> {

    public ColossalCogwheelRenderer(Context context) {
        super(context);
    };

    @Override
    protected void renderSafe(ColossalCogwheelBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = be.getBlockState();
        if (!ColossalCogwheelBlock.isController(state)) return;
        //if (Backend.canUseInstancing(be.getLevel())) return; //TODO instance
        BlockPos relativeCenter = ColossalCogwheelBlock.getRelativeCenterPosition(state);
        ms.pushPose();
        ms.translate(0f, -0.5f, 0f);
        SuperByteBuffer cogBuffer = getRotatedModel(be, state);
        cogBuffer.translate(relativeCenter);
        standardKineticRotationTransform(getRotatedModel(be, state), be, light);
        cogBuffer.translateBack(relativeCenter).centre();
        cogBuffer.renderInto(ms, buffer.getBuffer(RenderType.cutout()));
        ms.popPose();
    };
    
};
