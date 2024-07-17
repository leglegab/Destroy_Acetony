package com.petrolpark.destroy.compat.jei;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

import com.petrolpark.destroy.block.DestroyBlocks;

import net.minecraft.world.item.ItemStack;

/**
 * This class will be loaded without the guarantee that JEI is installed.
 */
public class DestroyJEISetup {
    
    /**
     * Any Item which can be filled with Explosives.
     */
    public static final Collection<Supplier<ItemStack>> CUSTOM_MIX_EXPLOSIVES = new HashSet<>();
    static {
        CUSTOM_MIX_EXPLOSIVES.add(DestroyBlocks.CUSTOM_EXPLOSIVE_MIX::asStack);
    };
};
