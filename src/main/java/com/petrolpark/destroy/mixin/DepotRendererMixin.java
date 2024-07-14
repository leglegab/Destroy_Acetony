package com.petrolpark.destroy.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.Destroy;
import com.simibubi.create.content.logistics.depot.DepotRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(DepotRenderer.class)
public class DepotRendererMixin {
    
    @Redirect(
        method = "renderItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic"
        ),
        remap = false
    )
    private static void renderOnBelt(ItemRenderer renderer, ItemStack pItemStack, ItemDisplayContext pDiplayContext, int pCombinedLight, int pCombinedOverlay, PoseStack pPoseStack, MultiBufferSource pBuffer, @Nullable Level pLevel, int pSeed) {
        renderer.renderStatic(pItemStack, Destroy.BELT_DISPLAY_CONTEXT, pCombinedLight, pCombinedOverlay, pPoseStack, pBuffer, pLevel, pSeed);
    };
};
