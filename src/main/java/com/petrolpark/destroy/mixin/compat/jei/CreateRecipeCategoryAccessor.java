package com.petrolpark.destroy.mixin.compat.jei;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import net.minecraft.world.item.crafting.Recipe;

@Mixin(CreateRecipeCategory.class)
public interface CreateRecipeCategoryAccessor {

    @Accessor(
        value = "recipes",
        remap = false
    )
    public Supplier<List<? extends Recipe<?>>> getRecipes();

    @Accessor(
        value = "recipes",
        remap = false
    )
    public void setRecipes(Supplier<List<? extends Recipe<?>>> recipes);
};
