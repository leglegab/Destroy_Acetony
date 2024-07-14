package com.petrolpark.destroy.item.renderer;

import java.util.Map;
import java.util.ArrayList;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.client.model.DummyBaker;
import com.petrolpark.destroy.item.CircuitMaskItem;
import com.petrolpark.destroy.item.CircuitPatternItem;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator; 
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Destroy.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CircuitPatternItemRenderer extends CustomRenderedItemModelRenderer {

    protected final ResourceLocation fragmentTextureResourceLocation;

    public CircuitPatternItemRenderer(ResourceLocation fragmentTextureResourceLocation) {
        this.fragmentTextureResourceLocation = fragmentTextureResourceLocation;
    };

    @OnlyIn(Dist.CLIENT)
    public BakedModel[] models = null;

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (models == null) generateModels(model.getOriginalModel());
        ms.pushPose();
        Minecraft mc = Minecraft.getInstance();

        if (transformType == Destroy.BELT_DISPLAY_CONTEXT) {
            if (stack.getOrCreateTag().contains("Flipped")) TransformStack.cast(ms).rotateY(180);
        };

        ItemRenderer itemRenderer = mc.getItemRenderer();
        itemRenderer.render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, model.getOriginalModel()); // Render the Item normally
        int pattern = (stack.getItem() instanceof CircuitPatternItem item ? CircuitPatternItem.getPattern(stack): 0);
        for (int i = 0; i < 16; i++) {
            if (CircuitMaskItem.isPunched(pattern, i)) continue;
            itemRenderer.render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, models[i]);
        };

        ms.popPose();
    };

    private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void generateModels(BakedModel originalModel) {
        models = new BakedModel[16];
        for (int i = 0; i < 16; i++) {
            final String number = String.valueOf(i);
            ResourceLocation rl = fragmentTextureResourceLocation.withPath(p -> p.concat("/" + number));
            models[i] = DummyBaker.bake(
                ITEM_MODEL_GENERATOR.generateBlockModel(
                    Material::sprite, // Tell the Item Model Generator how to fetch the sprite
                    new BlockModel(
                        rl,
                        new ArrayList<>(), // No elements - these get added by the Item Model Generator
                        Map.of("layer0", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, rl))),
                        null,
                        null,
                        originalModel.getTransforms(), // The Item Model Generator sets these
                        new ArrayList<>()
                    )
                ),
                rl
            );
        };
    };

    @SubscribeEvent
    public void onBakingCompleted(ModelEvent.BakingCompleted event) {
        models = null; // Refresh models if the resource pack is reloaded
    };
    
};
