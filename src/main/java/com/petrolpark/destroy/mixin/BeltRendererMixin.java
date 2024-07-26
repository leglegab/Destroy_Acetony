package com.petrolpark.destroy.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.DestroyItemDisplayContexts;
import com.simibubi.create.content.kinetics.belt.BeltRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(BeltRenderer.class)
public class BeltRendererMixin {
    
    @Redirect(
        method = "renderItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic"
        ),
        remap = false
    )
    private void renderOnBelt(ItemRenderer renderer, @Nullable LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDiplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, @Nullable Level pLevel, int pCombinedLight, int pCombinedOverlay, int pSeed) {
        renderer.renderStatic(pItemStack, DestroyItemDisplayContexts.BELT, pCombinedLight, pCombinedOverlay, pPoseStack, pBuffer, pLevel, pSeed);
    };
};
