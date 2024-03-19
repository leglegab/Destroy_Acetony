package com.petrolpark.destroy.recipe;

import javax.annotation.Nullable;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.item.DestroyItems;
import com.petrolpark.destroy.item.DiscStamperItem;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

public class DiscStampingRecipe extends DeployerApplicationRecipe {

    public DiscStampingRecipe(ProcessingRecipeParams params) {
        super(params);
    };

    @Nullable
    public static DeployerApplicationRecipe create(ItemStack discStamper) {
        if (!(discStamper.getItem() instanceof DiscStamperItem)) return null;
        ItemStack disc = DiscStamperItem.getDisc(discStamper);
        if (disc.isEmpty()) return null;
        return new ProcessingRecipeBuilder<>(DiscStampingRecipe::new, Destroy.asResource("disc_stamping_"+Item.getId(disc.getItem())))
            .require(Ingredient.of(DestroyItems.BLANK_MUSIC_DISC))
            .require(StrictNBTIngredient.of(discStamper))
            .output(disc)
            .toolNotConsumed()
            .build();
    };

    @Override
    public boolean supportsAssembly() {
        return false;
    };
    
};
