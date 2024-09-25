package com.petrolpark.destroy.block.renderer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.block.TreeTapBlock;
import com.petrolpark.destroy.block.entity.TreeTapBlockEntity;
import com.petrolpark.destroy.block.model.DestroyPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;    
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class TreeTapRenderer extends KineticBlockEntityRenderer<TreeTapBlockEntity> {

    public TreeTapRenderer(Context context) {
        super(context);
    };

    @Override
	protected void renderSafe(TreeTapBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

		if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState state = be.getBlockState();
        Direction facing = state.getValue(TreeTapBlock.HORIZONTAL_FACING);
		SuperByteBuffer armRenderer = CachedBufferer.partial(DestroyPartials.TREE_TAP_ARM, state);
        armRenderer
            .centre()
            .rotate(9f * Mth.sin(getAngleForTe(be, be.getBlockPos(), facing.getClockWise().getAxis())), facing.getClockWise().getAxis())
            .rotateToFace(facing.getOpposite())
            .unCentre()
            .translate(0f, 12 / 16f, 7 / 16f)
            .light(light)
            .renderInto(ms, buffer.getBuffer(RenderType.solid()));
            
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
	};

    @Override
	protected BlockState getRenderedBlockState(TreeTapBlockEntity be) {
		return shaft(getRotationAxisOf(be));
	};
    
};
