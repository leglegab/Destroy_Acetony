package com.petrolpark.destroy.compat.tfmg;

import com.drmangotea.tfmg.recipes.distillation.AdvancedDistillationRecipe;
import com.drmangotea.tfmg.recipes.distillation.DistillationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;

public class DistillationRecipeConversion {
    
    public static DistillationRecipe convertToDistillationRecipe(ProcessingRecipe<?> recipe) {
        ProcessingRecipeBuilder<DistillationRecipe> builder = new ProcessingRecipeBuilder<>(DistillationRecipe::new, recipe.getId())
            .withFluidIngredients(recipe.getFluidIngredients())
            .withFluidOutputs(recipe.getFluidResults())
            .withItemOutputs(ProcessingOutput.EMPTY, ProcessingOutput.EMPTY);
        return builder.build();
    };

    public static AdvancedDistillationRecipe convertToAdvancedDistillationRecipe(ProcessingRecipe<?> recipe) {
        ProcessingRecipeBuilder<AdvancedDistillationRecipe> builder = new ProcessingRecipeBuilder<>(AdvancedDistillationRecipe::new, recipe.getId())
            .withFluidIngredients(recipe.getFluidIngredients())
            .withFluidOutputs(recipe.getFluidResults());
        return builder.build();
    };
};
