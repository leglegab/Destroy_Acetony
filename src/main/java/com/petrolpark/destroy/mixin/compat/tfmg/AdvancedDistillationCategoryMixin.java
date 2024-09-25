package com.petrolpark.destroy.mixin.compat.tfmg;

import org.spongepowered.asm.mixin.Mixin;

import com.drmangotea.tfmg.recipes.distillation.DistillationRecipe;
import com.drmangotea.tfmg.recipes.jei.AdvancedDistillationCategory;
import com.petrolpark.destroy.compat.tfmg.DistillationRecipeConversion;
import com.petrolpark.destroy.compat.tfmg.SharedDistillationRecipes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;

@Mixin(AdvancedDistillationCategory.class)
public abstract class AdvancedDistillationCategoryMixin extends CreateRecipeCategory<DistillationRecipe> {

    public AdvancedDistillationCategoryMixin(Info<DistillationRecipe> info) {
        super(info);
    };

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        super.registerRecipes(registration);
        Minecraft mc = Minecraft.getInstance();
        registration.addRecipes(getRecipeType(), SharedDistillationRecipes.getDestroyToTFMGRecipes(mc.level).stream().map(DistillationRecipeConversion::convertToDistillationRecipe).toList());
    };
};
