package com.petrolpark.destroy.chemistry.naming;

import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;

import net.minecraft.network.chat.Component;

/**
 * Something which has a name and can be found in a solution. This is typically a single {@link LegacySpecies} or a salt.
 */
public interface INameableProduct {
    
    /**
     * The display name of this product.
     * @param iupac Whether the IUPAC systematic name should be used rather than the common name
     */
    public Component getName(boolean iupac); 
};
