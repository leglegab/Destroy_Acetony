package com.petrolpark.destroy.item.renderer;

import java.util.ArrayList;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.client.model.DummyBaker;
import com.petrolpark.destroy.item.DestroyItems;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TestTubeRenderer extends CustomRenderedItemModelRenderer {

    private static final ResourceLocation defaultItemModelRl = new ResourceLocation("generated");
    private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
    private static BakedModel overlayModel;

    @SuppressWarnings("deprecation")
    private static BakedModel getModel() {
        if (overlayModel == null) {
            Material trimTexture = new Material(TextureAtlas.LOCATION_BLOCKS, Destroy.asResource("item/test_tube_overlay"));
            overlayModel = DummyBaker.bake(
            ITEM_MODEL_GENERATOR.generateBlockModel(
                Material::sprite, // Tell the Item Model Generator how to fetch the sprite
                new BlockModel(
                    defaultItemModelRl,
                    new ArrayList<>(), // No elements - these get added by the Item Model Generator
                    Map.of("layer0", Either.left(trimTexture)),
                    null,
                    null,
                    ItemTransforms.NO_TRANSFORMS, // These aren't needed as the model will be composited - the Item Model Generator sets them anyway
                    new ArrayList<>()
                )
            ),
            Destroy.asResource("test_tube_overlay")
        );
        };
        return overlayModel;
    };

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, model.getOriginalModel());
        TransparentItemRenderer.transformAndRenderModel(getModel(), transformType, DestroyItems.TEST_TUBE.get().getColor(stack), light, overlay, ms, buffer);
    };
    
};
