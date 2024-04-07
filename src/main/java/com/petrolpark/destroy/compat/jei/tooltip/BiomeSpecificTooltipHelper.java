package com.petrolpark.destroy.compat.jei.tooltip;

import java.util.ArrayList;
import java.util.List;

import com.petrolpark.destroy.recipe.IBiomeSpecificProcessingRecipe;
import com.petrolpark.destroy.util.DestroyLang;
import com.simibubi.create.foundation.utility.Components;

import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class BiomeSpecificTooltipHelper {
    
    public static IRecipeSlotTooltipCallback getAllowedBiomeList(IBiomeSpecificProcessingRecipe recipe) {
        Minecraft minecraft = Minecraft.getInstance();
        RegistryAccess registryAccess = minecraft.level.registryAccess();
        List<ResourceLocation> biomes = new ArrayList<>();
        recipe.getAllowedBiomes().stream().forEach(bv -> biomes.addAll(bv.getBiomes(registryAccess).stream().map(biome -> registryAccess.registryOrThrow(Registries.BIOME).getKey(biome)).toList()));
        return (view, tooltip) -> {
            tooltip.clear();
            tooltip.add(DestroyLang.translate("tooltip.biome_specific_recipe").style(ChatFormatting.WHITE).component());
            biomes.forEach(biome -> tooltip.add(DestroyLang.builder().space().add(Components.translatable(biome.toLanguageKey("biome"))).style(ChatFormatting.GRAY).component()));
        };
    };
};
