package com.petrolpark.destroy.item.renderer;

import com.jozufozu.flywheel.util.AnimationTickHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SeismographItemRenderer extends CustomRenderedItemModelRenderer {

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
            handItemRenderer.renderOneHandedMap(ms, buffer, light, equippedProgress, arm, swingProgress, stack);
            ms.pushPose();
            ms.pushPose();
            ms.pushPose();
        } else {
            itemRenderer.render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, model.getOriginalModel());
        };
    };
    
};
