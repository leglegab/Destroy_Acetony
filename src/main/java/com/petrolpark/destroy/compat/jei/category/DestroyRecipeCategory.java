package com.petrolpark.destroy.compat.jei.category;

import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.compat.jei.animation.DestroyGuiTextureDrawable;
import com.petrolpark.destroy.compat.jei.tooltip.BiomeSpecificTooltipHelper;
import com.petrolpark.destroy.item.DestroyItems;
import com.petrolpark.destroy.recipe.IBiomeSpecificProcessingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.Recipe;

public abstract class DestroyRecipeCategory<T extends Recipe<?>> extends CreateRecipeCategory<T> {

    protected final IJeiHelpers helpers;

    public DestroyRecipeCategory(Info<T> info, IJeiHelpers helpers) {
        super(info);
        this.helpers = helpers;
    };

    public interface Factory<T extends Recipe<?>> {
		CreateRecipeCategory<T> create(Info<T> info, IJeiHelpers helpers);
	};

    public static void addOptionalRequiredBiomeSlot(IRecipeLayoutBuilder builder, IBiomeSpecificProcessingRecipe recipe, int x, int y) {
        if (!recipe.getAllowedBiomes().isEmpty())builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y)
            .setBackground(getRenderedSlot(), -1, -1)
            .setOverlay(DestroyGuiTextureDrawable.of(DestroyGuiTextures.GLOBE), 0, 1)
            .addItemStack(DestroyItems.ABS.asStack()) // Dummy item so we actually get something generated
            .addTooltipCallback(BiomeSpecificTooltipHelper.getAllowedBiomeList(recipe)); 
    };
    
};
