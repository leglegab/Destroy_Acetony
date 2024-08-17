package com.petrolpark.destroy.recipe;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.item.IDecayingItem;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class DecayingItemCookingRecipe<R extends AbstractCookingRecipe> extends AbstractCookingRecipe {

    protected final R wrappedRecipe;
    protected final RecipeSerializer<R> wrappedSerializer;

    public DecayingItemCookingRecipe(R wrappedRecipe, RecipeSerializer<R> serializer) {
        super(wrappedRecipe.getType(), wrappedRecipe.getId(), wrappedRecipe.getGroup(), wrappedRecipe.category(), wrappedRecipe.getIngredients().get(0), wrappedRecipe.getResultItem(null), wrappedRecipe.getExperience(), wrappedRecipe.getCookingTime());
        this.wrappedRecipe = wrappedRecipe;
        this.wrappedSerializer = serializer;
    };

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return withDecay(super.assemble(container, registryAccess));
    };

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return withDecay(super.getResultItem(registryAccess));
    };

    protected ItemStack withDecay(ItemStack stack) {
        if (!Destroy.DECAYING_ITEM_HANDLER.get().isClientSide()) {
            ItemStack copy = stack.copy();
            IDecayingItem.startDecay(copy, 0l);
            return copy;
        } else {
            return stack;
        }
    };

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DestroyRecipeTypes.DECAYING_ITEM_COOKING.getSerializer();
    };

    public static class Serializer implements RecipeSerializer<DecayingItemCookingRecipe<? extends AbstractCookingRecipe>> {

        @Override
        public DecayingItemCookingRecipe<?> fromJson(ResourceLocation recipeId, JsonObject json) {
            return fromJsonInternal(recipeId, json);
        };

        protected <R extends AbstractCookingRecipe> DecayingItemCookingRecipe<R> fromJsonInternal(ResourceLocation recipeId, JsonObject json) {
            RecipeSerializer<R> serializer = getWrappedSerializer(new ResourceLocation(GsonHelper.getAsString(json, "serializer")));
            return new DecayingItemCookingRecipe<>(serializer.fromJson(recipeId, json), serializer);
        };

        @Override
        public @Nullable DecayingItemCookingRecipe<?> fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return fromNetworkInternal(recipeId, buffer);
        };

        protected <R extends AbstractCookingRecipe> DecayingItemCookingRecipe<R> fromNetworkInternal(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            RecipeSerializer<R> serializer = getWrappedSerializer(buffer.readResourceLocation());
            return new DecayingItemCookingRecipe<>(serializer.fromNetwork(recipeId, buffer), serializer);
        };

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DecayingItemCookingRecipe<?> recipe) {
            toNetworkInternal(buffer, recipe);
        };

        protected <R extends AbstractCookingRecipe> void toNetworkInternal(FriendlyByteBuf buffer, DecayingItemCookingRecipe<R> recipe) {
            buffer.writeRegistryId(ForgeRegistries.RECIPE_SERIALIZERS, recipe.wrappedSerializer);
            recipe.wrappedSerializer.toNetwork(buffer, recipe.wrappedRecipe);
        };

        @SuppressWarnings("unchecked")
        protected <R extends AbstractCookingRecipe> RecipeSerializer<R> getWrappedSerializer(ResourceLocation id) {
            try {
                return (RecipeSerializer<R>)Optional.ofNullable(ForgeRegistries.RECIPE_SERIALIZERS.getValue(id)).orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe serializer '" + id.toString() + "'"));
            } catch (ClassCastException e) {
                throw new JsonSyntaxException("Recipe serializer '" + id.toString() + "' does not give a cooking recipe.");
            }
        };

    };
    
};
