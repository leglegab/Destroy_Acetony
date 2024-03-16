package com.petrolpark.destroy.mixin.compat.tfmg;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.drmangotea.tfmg.blocks.machines.oil_processing.distillation.distillation_tower.DistillationControllerBlockEntity;
import com.drmangotea.tfmg.blocks.machines.oil_processing.distillation.distillation_tower.DistillationOutputBlockEntity;
import com.drmangotea.tfmg.recipes.distillation.AdvancedDistillationRecipe;

@Mixin(DistillationOutputBlockEntity.class)
public class DistillationOutputBlockEntityMixin {
    
    @Inject(
        method = "Lcom/drmangotea/tfmg/blocks/machines/oil_processing/distillation/distillation_tower/DistillationOutputBlockEntity;getMatchingRecipes(Lcom/drmangotea/tfmg/blocks/machines/oil_processing/distillation/distillation_tower/DistillationControllerBlockEntity;)V",
        at = @At("TAIL"),
        cancellable = true,
        remap = false
    )
    public void inGetMatchingRecipes(DistillationControllerBlockEntity be, CallbackInfoReturnable<AdvancedDistillationRecipe> cir) {
        //TODO reimplement once TFMG distillation is fixed
        // if (cir.getReturnValue() == null) {
        //     List<AdvancedDistillationRecipe> recipes = SharedDistillationRecipes.getDestroyToTFMGRecipes(be.getLevel()).stream().map(DistillationRecipeConversion::convertToAdvancedDistillationRecipe).filter(recipe -> recipe.getFluidIngredients().get(0).test(be.inputTank.getPrimaryHandler().getFluid())).toList();
        //     if (recipes.size() > 0) {
        //         cir.setReturnValue(recipes.get(0));
        //         cir.cancel();
        //     };
        // };
    };
};
