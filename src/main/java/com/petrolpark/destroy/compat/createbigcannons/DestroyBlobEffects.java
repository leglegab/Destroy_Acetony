package com.petrolpark.destroy.compat.createbigcannons;

import com.petrolpark.destroy.fluid.DestroyFluids;
import com.petrolpark.destroy.util.PollutionHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fluids.FluidStack;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.EndFluidStack;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidBlobEffectRegistry;

public class DestroyBlobEffects {
    
    public static void registerBlobEffects() {
        //FluidBlobEffectRegistry.registerAllHit(DestroyFluids.MIXTURE.get(), DestroyBlobEffects::onMixtureHit);
    };

    // public static void onMixtureHit(EndFluidStack fstack, FluidBlob projectile, Level level, HitResult result) {
    //     FluidStack stack = new FluidStack(fstack.fluid(), fstack.amount());
    //     stack.setTag(fstack.data());
    //     PollutionHelper.pollute(level, BlockPos.containing(result.getLocation()), stack);
    // };
};
