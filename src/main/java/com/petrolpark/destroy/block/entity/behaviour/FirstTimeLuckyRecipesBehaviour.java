package com.petrolpark.destroy.block.entity.behaviour;

import java.util.function.Predicate;

import com.petrolpark.destroy.capability.player.PlayerLuckyFirstRecipes;
import com.petrolpark.destroy.recipe.IFirstTimeLuckyRecipe;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.recipe.RecipeFinder;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.util.LazyOptional;

public class FirstTimeLuckyRecipesBehaviour extends AbstractRememberPlacerBehaviour {

    public static final BehaviourType<FirstTimeLuckyRecipesBehaviour> TYPE = new BehaviourType<>();

    private final Object recipeCacheKey = new Object();
    private final Predicate<Recipe<?>> recipeFilter;

    /**
     * Ensure this Block Entity remembers who placed it for the purposes of ensuring first-time-lucky
     * recipes award all outputs.
     * @param be
     * @param recipeFilter all recipes which match this filter and implement {@link com.petrolpark.destroy.recipe.IFirstTimeLuckyRecipe IFirstTimeLuckyRecipe}
     * will be checked - if there is at least one of them we haven't done, we'll remember the player
     */
    public FirstTimeLuckyRecipesBehaviour(SmartBlockEntity be, Predicate<Recipe<?>> recipeFilter) {
        super(be);
        this.recipeFilter = recipeFilter;
    };

    @Override
    public boolean shouldRememberPlacer(Player placer) {
        LazyOptional<PlayerLuckyFirstRecipes> plfrOp = placer.getCapability(PlayerLuckyFirstRecipes.Provider.PLAYER_LUCKY_FIRST_RECIPES);
        if (!plfrOp.isPresent()) return true;
        PlayerLuckyFirstRecipes plfr = plfrOp.resolve().get();
        return !RecipeFinder.get(recipeCacheKey, getWorld(), recipeFilter.and(r -> r instanceof IFirstTimeLuckyRecipe)).stream().map(Recipe::getId).allMatch(plfr::contains);
    };

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    };
    
};
