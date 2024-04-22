package com.petrolpark.destroy.recipe;

import javax.annotation.Nullable;

import com.petrolpark.destroy.item.ICustomExplosiveMixItem;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

public class FillCustomExplosiveMixItemRecipe extends CustomRecipe {

    public static final RecipeSerializer<FillCustomExplosiveMixItemRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(FillCustomExplosiveMixItemRecipe::new);

    public FillCustomExplosiveMixItemRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    };

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        return assemble(container, null) != ItemStack.EMPTY;
    };

    @Override
    public ItemStack assemble(CraftingContainer container, @Nullable RegistryAccess registryAccess) {
        boolean anyExplosiveFound = false;
        ItemStack mixItem = ItemStack.EMPTY;
        CustomExplosiveMixInventory inv = null;
        for (boolean findMixItem : Iterate.trueAndFalse) {
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack stack = container.getItem(slot);
                if (stack.getItem() instanceof ICustomExplosiveMixItem customMixItem) { 
                    if (findMixItem) { // If we're looking for a mix container and we've found one
                        if (inv != null) return ItemStack.EMPTY; // Only one mix container allowed
                        else inv = customMixItem.getExplosiveInventory(stack);
                    };
                } else if (CustomExplosiveMixInventory.canBeAdded(stack)) {
                    anyExplosiveFound = true;
                    if (!findMixItem && inv != null && ItemHandlerHelper.insertItem(inv, stack, false) != ItemStack.EMPTY) return ItemStack.EMPTY; 
                } else {
                    return ItemStack.EMPTY;
                };
            };
        };
        if (!anyExplosiveFound || mixItem.isEmpty()) return ItemStack.EMPTY; // If a mix Item or explosive was never found
        ItemStack result = mixItem.copy();
        if (result.getItem() instanceof ICustomExplosiveMixItem customMixItem) customMixItem.setExplosiveInventory(result, inv); // Check should never fail
        return result;
    };

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    };

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    };
    
};
