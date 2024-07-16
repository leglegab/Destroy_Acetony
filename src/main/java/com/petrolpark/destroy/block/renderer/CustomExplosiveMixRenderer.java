package com.petrolpark.destroy.block.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.block.entity.CustomExplosiveMixBlockEntity;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class CustomExplosiveMixRenderer extends SafeBlockEntityRenderer<CustomExplosiveMixBlockEntity> {

    public CustomExplosiveMixRenderer(BlockEntityRendererProvider.Context context) {};

    @Override
    protected void renderSafe(CustomExplosiveMixBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Component nameComponent = be.getCustomName();
        if (nameComponent == null) return;
        renderTruncated(ms, bufferSource, light, nameComponent.getString());
    };
    
    public static final ResourceLocation FONT_LOCATION = Destroy.asResource("explosive");
    public static final Style FONT = Style.EMPTY.withFont(FONT_LOCATION);
    public static final String ALLOWED_CHARACTERS = " 1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    public static void renderTruncated(PoseStack ms, MultiBufferSource buffer, int light, String string) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        string = string.toUpperCase(mc.getLocale());

        String result = "";
        int width = 0;
        for (int i = 0; i < string.length(); i++) {
            String s = string.substring(i, i + 1);
            if (!ALLOWED_CHARACTERS.contains(s)) continue;
            int sWidth = font.width(FormattedText.of(s, FONT));
            if (width + sWidth > 16) continue;
            result += s;
            width += sWidth;
        };
        
        for (Direction face : Iterate.horizontalDirections) {
            ms.pushPose();
            TransformStack.cast(ms)
                .centre()
                .scale(- 1 / 16f)
                .rotateToFace(face);
            ms.translate(-8d, -5d, 8.02d);
            font.drawInBatch(FormattedCharSequence.forward(result, Style.EMPTY.withFont(FONT_LOCATION)), (17 - width) / 2, 0, 0xFFFFFF, false, ms.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, light);
            ms.popPose();
        };
        
        if (buffer instanceof BufferSource bufferSource) {
			bufferSource.endBatch(font.getFontSet(FONT_LOCATION).whiteGlyph().renderType(Font.DisplayMode.NORMAL));
		};
    };
};
