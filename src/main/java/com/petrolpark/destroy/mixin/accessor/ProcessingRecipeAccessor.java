package com.petrolpark.destroy.mixin.accessor;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

import net.minecraft.world.item.ItemStack;

@Mixin(ProcessingRecipe.class)
public interface ProcessingRecipeAccessor {
    
    @Accessor("forcedResult")
    public Supplier<ItemStack> getForcedResult();
};
