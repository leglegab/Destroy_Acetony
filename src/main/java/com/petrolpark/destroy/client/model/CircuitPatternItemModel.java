package com.petrolpark.destroy.client.model;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

public class CircuitPatternItemModel implements IUnbakedGeometry<CircuitPatternItemModel> {

    public final ResourceLocation fragmentTextureResourceLocation;
    public final IUnbakedGeometry<?> wrapped;

    public static final ModelProperty<ResourceLocation> FRAGMENT_TEXTURE_RESOURCE_LOCATION_PROPERTY = new ModelProperty<>();

    public CircuitPatternItemModel(IUnbakedGeometry<?> wrapped, ResourceLocation fragmentTextureResourceLocation) {
        this.wrapped = wrapped;
        this.fragmentTextureResourceLocation = fragmentTextureResourceLocation;
    };

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        return new Baked(wrapped.bake(context, baker, spriteGetter, modelState, overrides, modelLocation));
    };

    public class Baked extends BakedModelWrapper<BakedModel> {
        
        public Baked(BakedModel originalModel) {
            super(originalModel);
        };

        @Override
        public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
            return ModelData.builder().with(FRAGMENT_TEXTURE_RESOURCE_LOCATION_PROPERTY, fragmentTextureResourceLocation).build();
        };
    };

    public static class Loader implements IGeometryLoader<CircuitPatternItemModel> {

        @Override
        public CircuitPatternItemModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            ItemLayerModel itemModel = ItemLayerModel.Loader.INSTANCE.read(jsonObject, deserializationContext);
            if (jsonObject.has("fragment_textures")) {
                try {
                    return new CircuitPatternItemModel(itemModel, new ResourceLocation(jsonObject.get("fragment_textures").getAsString()));
                } catch (UnsupportedOperationException | IllegalStateException e) {
                    throw new JsonParseException("Cannot read Circuit Pattern item model", e);
                }
            } else {
                throw new JsonParseException("Circuit Pattern item models must refer to a texture of fragments");
            }
        };

    };
    
};
