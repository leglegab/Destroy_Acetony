package com.petrolpark.destroy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.petrolpark.destroy.fluid.DestroyFluids;
import com.simibubi.create.content.logistics.filter.FilterItemStack;

import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

@Mixin(FilterItemStack.class)
public class FilterItemStackMixin {
    
    @Inject(
        method = "Lcom/simibubi/create/content/logistics/filter/FilterItemStack;test(Lnet/minecraft/world/level/Level;Lnet/minecraftforge/fluids/FluidStack;Z)Z",
        at = @At("HEAD"),
        remap = false
    )
    public void inTest(Level level, FluidStack stack, boolean matchNBT, CallbackInfoReturnable<Boolean> cir) {
        if (matchNBT && DestroyFluids.isMixture(stack)) cir.setReturnValue(false);
    };
};
