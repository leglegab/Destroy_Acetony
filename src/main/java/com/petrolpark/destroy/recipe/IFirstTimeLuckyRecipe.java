package com.petrolpark.destroy.recipe;

import java.util.List;

import com.petrolpark.destroy.capability.player.PlayerLuckyFirstRecipes;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public interface IFirstTimeLuckyRecipe<T extends ProcessingRecipe<?>> {
    
    T getAsRecipe();

    public default List<ItemStack> rollLuckyResults(Player player) {
        ProcessingRecipe<?> recipe = getAsRecipe();
        if (player == null) return recipe.rollResults();
        LazyOptional<PlayerLuckyFirstRecipes> plfrOp = player.getCapability(PlayerLuckyFirstRecipes.Provider.PLAYER_LUCKY_FIRST_RECIPES);
        if (!plfrOp.isPresent()) return recipe.rollResults();
        PlayerLuckyFirstRecipes plfr = plfrOp.resolve().get();
        if (plfr.contains(recipe.getId())) return recipe.rollResults(); // Only guarantee 100% success the first time
        plfr.add(recipe.getId()); // Record this recipe so we only get the bonus output once
        return recipe.getRollableResults().stream().map(ProcessingOutput::getStack).toList();
    };
};
