package com.petrolpark.destroy.compat.jei.category;

import com.petrolpark.destroy.compat.jei.animation.AnimatedDynamo;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public class ArcFurnaceCategory extends BasinCategory {

    private final AnimatedDynamo dynamo;

    protected final RecipeType<BasinRecipe> type;

    public ArcFurnaceCategory(Info<BasinRecipe> info) {
        super(info, false);
        dynamo = new AnimatedDynamo(true, true);
        type = info.recipeType();
    };

    public static <R extends AbstractCookingRecipe> BasinRecipe toBasinRecipe(R recipe) {
        Minecraft mc = Minecraft.getInstance();
        return new ProcessingRecipeBuilder<>(BasinRecipe::new, recipe.getId().withPath(path -> "arc_furnace_"+path))
            .withItemIngredients(recipe.getIngredients())
            .output(recipe.getResultItem(mc.level.registryAccess()))
            .duration(recipe.getCookingTime())
            .build();
    };

    @Override
    public void draw(BasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        dynamo.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    };
    
};
