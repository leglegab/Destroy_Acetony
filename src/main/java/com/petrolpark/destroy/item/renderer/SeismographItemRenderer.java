package com.petrolpark.destroy.item.renderer;

import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.item.SeismographItem;
import com.petrolpark.destroy.item.SeismographItem.Seismograph;
import com.petrolpark.destroy.item.SeismographItem.Seismograph.Mark;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class SeismographItemRenderer extends CustomRenderedItemModelRenderer {

    public static final RenderType BACKGROUND = DestroyGuiTextures.SEISMOGRAPH_BACKGROUND.asTextRenderType();
    public static final RenderType OVERLAY = DestroyGuiTextures.SEISMOGRAPH_OVERLAY.asTextRenderType();
    public static final RenderType TICK = DestroyGuiTextures.SEISMOGRAPH_TICK.asTextRenderType();
    public static final RenderType CROSS = DestroyGuiTextures.SEISMOGRAPH_CROSS.asTextRenderType();
    public static final RenderType GUESSED_TICK = DestroyGuiTextures.SEISMOGRAPH_GUESSED_TICK.asTextRenderType();
    public static final RenderType GUESSED_CROSS = DestroyGuiTextures.SEISMOGRAPH_GUESSED_CROSS.asTextRenderType();

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ItemRenderer itemRenderer = mc.getItemRenderer();
        ItemInHandRenderer handItemRenderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();
        float partialTicks = AnimationTickHolder.getPartialTicks();

        if (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            // Logic replicated from ItemInHandRenderer
            InteractionHand swingingHand = player.swingingArm;
            HumanoidArm arm = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
            InteractionHand hand = arm == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            if (swingingHand == null) swingingHand = InteractionHand.MAIN_HAND;
            float equippedProgress = 1f - (hand == InteractionHand.MAIN_HAND ? Mth.lerp(partialTicks, handItemRenderer.oMainHandHeight, handItemRenderer.mainHandHeight) : Mth.lerp(partialTicks, handItemRenderer.oOffHandHeight, handItemRenderer.offHandHeight));
            float swingProgress = swingingHand == hand ? player.getAttackAnim(partialTicks) : 0f;

            // Undo the Item Stack model transforms
            ms.popPose();
            ms.popPose();
            ms.popPose();
            renderOneHandedSeismograph(ms, buffer, light, equippedProgress, arm, swingProgress, stack, mc, handItemRenderer);
            ms.pushPose();
            ms.pushPose();
            ms.pushPose();
        } else {
            itemRenderer.render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, model.getOriginalModel());
        };
    };

    /**
     * Largely copied from the {@link net.minecraft.client.renderer.ItemInHandRenderer#renderOneHandedMap Minecraft source code}.
     */
    public static void renderOneHandedSeismograph(PoseStack ms, MultiBufferSource buffer, int light, float equippedProgress, HumanoidArm hand, float swingProgress, ItemStack stack, Minecraft mc, ItemInHandRenderer itemRenderer) {
        float handRotation = hand == HumanoidArm.RIGHT ? 1f : -1f;
        ms.translate(handRotation * 0.125f, -0.125f, 0f);
        if (!mc.player.isInvisible()) {
            ms.pushPose();
            ms.mulPose(Axis.ZP.rotationDegrees(handRotation * 10f));
            itemRenderer.renderPlayerArm(ms, buffer, light, equippedProgress, swingProgress, hand);
            ms.popPose();
        };
        ms.pushPose();
        ms.translate(handRotation * 0.51f, -0.08f + equippedProgress * -1.2f, -0.75f);
        float sqrtSwing = Mth.sqrt(swingProgress);
        float swingAngle = Mth.sin(sqrtSwing * (float)Math.PI);
        ms.translate(handRotation * -0.5f * swingAngle, 0.4f * Mth.sin(sqrtSwing * ((float)Math.PI * 2f)) - 0.3f * swingAngle, -0.3f * Mth.sin(swingProgress * (float)Math.PI));
        ms.mulPose(Axis.XP.rotationDegrees(swingAngle * -45f));
        ms.mulPose(Axis.YP.rotationDegrees(sqrtSwing * swingAngle * -30f));
        renderSeismograph(ms, buffer, light, stack, mc);
        ms.popPose();
    };

    /**
     * Largely copied from the {@link net.minecraft.client.renderer.ItemInHandRenderer#renderMap Minecraft source code}.
     */
    public static void renderSeismograph(PoseStack ms, MultiBufferSource buffer, int light, ItemStack stack, Minecraft mc) {
        ms.mulPose(Axis.YP.rotationDegrees(180f));
        ms.mulPose(Axis.ZP.rotationDegrees(180f));
        ms.scale(0.38f, 0.38f, 0.38f);
        ms.translate(-0.5f, -0.5f, 0f);
        ms.scale(1 / 128f, 1 / 128f, 1 / 128f);

        Integer mapId = SeismographItem.getMapId(stack);
        MapItemSavedData mapData = SeismographItem.getSavedData(mapId, mc.level);
        Seismograph seismograph = SeismographItem.readSeismograph(stack);

        // Scale to the size of the vanilla map
        ms.translate(-7f, -7f, 0f);
        ms.scale(142 / 64f, 142 / 64f, 1f);
        ms.pushPose(); {

            // Background
            DestroyGuiTextures.SEISMOGRAPH_BACKGROUND.render(ms, 0f, 0f);

            // Map colors
            ms.pushPose();
            ms.translate(13f, 13f, 0f);
            ms.scale(47 / 128f, 47 / 128f, 1f);
            if (mapData != null) mc.gameRenderer.getMapRenderer().render(ms, buffer, mapId, mapData, false, light);
            ms.popPose();
        
            // Marks
            ms.pushPose();
            ms.translate(13f, 13f, -0.02f);
            ms.scale(5 / 16f, 5 / 16f, 1f);
            for (int x = 0; x < 8; x++) {
                for (int z = 0; z < 8; z++) {
                    Mark mark = seismograph.getMark(x, z);
                    if (mark != Mark.NONE) mark.icon.render(ms, x * 19.2f, z * 19.2f);
                };
            };
            ms.popPose();

            // Numbers
            ms.pushPose();;
            ms.translate(9f, 14f, -0.03f);
            ms.scale(0.5f, 0.5f, 1f);
            for (int z = 0; z < 8; z++) {
                ms.pushPose();
                ms.translate(0f, z * 12f, 0f);
                if (seismograph.isRowDiscovered(z)) {
                    int[] numbers = seismograph.getRowDisplayed(z);
                    //drawString(mc.font, Integer.toBinaryString(seismograph.getRows()[z]), ms, buffer, 0f, 0f, 0);
                    if (numbers[0] == 0) {
                        drawString(mc.font, "0", ms, buffer, 0f, 0f, 0x484263);
                    } else {
                        int i = 0;
                        for (int number : numbers) {
                            if (number != 0) {
                                drawString(mc.font, ""+number, ms, buffer, -5f * i, 0f, 0x484263);
                                i++;
                            };
                        };
                    };
                } else {
                    drawString(mc.font, "?", ms, buffer, 0f, 0f, 0x484263);
                };
                ms.popPose();
            };
            ms.popPose();

            // Columns
            ms.pushPose();;
            ms.translate(14f, 8f, -0.03f);
            ms.scale(0.5f, 0.5f, 1f);
            for (int x = 0; x < 8; x++) {
                ms.pushPose();
                ms.translate(x * 12f, 0f, 0f);
                if (seismograph.isColumnDiscovered(x)) {
                    int[] numbers = seismograph.getColumnDisplayed(x);
                    if (numbers[0] == 0) {
                        drawString(mc.font, "0", ms, buffer, 0f, 0f, 0x484263);
                    } else {
                        int i = 0;
                        for (int number : numbers) {
                            if (number != 0) {
                                drawString(mc.font, ""+number, ms, buffer, 0f, -5f * i, 0x484263);
                                i++;
                            };
                        };
                    };
                } else {
                    drawString(mc.font, "?", ms, buffer, 0f, 0f, 0x484263);
                };
                ms.popPose();
            };
            ms.popPose();

            // Overlay
            DestroyGuiTextures.SEISMOGRAPH_OVERLAY.render(ms, 0f, 0f);
        } ms.popPose();
    };

    public static void drawString(Font font, String text, PoseStack ms, MultiBufferSource bufferSource, float x, float y, int color) {
        font.drawInBatch(text, x, y, color, false, ms.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880, font.isBidirectional());
    };
    
};
