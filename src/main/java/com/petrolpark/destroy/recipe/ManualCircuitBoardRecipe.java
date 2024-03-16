package com.petrolpark.destroy.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.exceptionHandler_return;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.petrolpark.destroy.item.CircuitPatternItem;
import com.petrolpark.destroy.item.DestroyItems;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ManualCircuitBoardRecipe extends ManualOnlyShapedRecipe {

    protected static Ingredient MASK_INGREDIENT = new Ingredient(Stream.empty()) {};

    protected int[] maskPositions;

    private ManualCircuitBoardRecipe(ResourceLocation id, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result, int[] maskPositions) {
        super(id, "", CraftingBookCategory.MISC, width, height, recipeItems, result);
        if (!(result.getItem() instanceof CircuitPatternItem)) throw new IllegalArgumentException("Result item must be able to support circuit patterns.");
        this.maskPositions = maskPositions;
    };
    
    public static Map<String, Ingredient> keyFromJson(JsonObject keyEntry) {
        Map<String, Ingredient> map = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : keyEntry.entrySet()) {
            if (entry.getKey().length() != 1) throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            if ("#".equals(entry.getKey())) throw new JsonSyntaxException("Invalid key entry: '#' is reserved for the Circuit Mask(s).");
            if (" ".equals(entry.getKey())) throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), false));
        };

        map.put("#", MASK_INGREDIENT);
        map.put(" ", Ingredient.EMPTY);
        return map;
    };

    public static class Serializer implements RecipeSerializer<ManualCircuitBoardRecipe> {

        @Override
        public ManualCircuitBoardRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] rows = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int x = rows[0].length();
            int y = rows.length;
            NonNullList<Ingredient> list = ShapedRecipe.dissolvePattern(rows, map, x, y);

            List<Integer> maskPositions = new ArrayList<>(x * y);
            for (int i = 0; i < x * y; i++) if (list.get(i) == MASK_INGREDIENT) maskPositions.add(i);
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new ManualCircuitBoardRecipe(recipeId, x, y, list, result, maskPositions.stream().mapToInt(i -> i).toArray());
        };

        @Override
        public ManualCircuitBoardRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            int resultCount = buffer.readInt();
            NonNullList<Ingredient> list = NonNullList.withSize(i * j, Ingredient.EMPTY);
            int[] maskPositions = buffer.readVarIntArray(i * j);

            addEachIngredient: for (int k = 0; k < list.size(); k++) {
                for (int position : maskPositions) {
                    if (position == k) {
                        list.set(k, MASK_INGREDIENT);
                        continue addEachIngredient;
                    };
                };
                list.set(k, Ingredient.fromNetwork(buffer));
            };

            return new ManualCircuitBoardRecipe(recipeId, i, j, list, resultCount, maskPositions);
        };

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ManualCircuitBoardRecipe recipe) {
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());
            buffer.writeVarInt(recipe.resultCount);
            buffer.writeVarIntArray(recipe.maskPositions);

            int i = 0;
            writeEachIngredient: for (Ingredient ingredient : recipe.getIngredients()) {
                for (int position : recipe.maskPositions) {
                    if (position == i) continue writeEachIngredient;
                };
                ingredient.toNetwork(buffer);
                i++;
            };
        };
    };

};