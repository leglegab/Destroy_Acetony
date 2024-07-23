package com.petrolpark.destroy.chemistry.api.species.tag;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.util.ImmutableObjectIntMap;

/**
 * A {@link Map} of {@link INuclide}s to a number of {@link IAtom}s of that {@link INuclide}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IMolecularFormula<N extends INuclide> extends ImmutableObjectIntMap<N> {
    
    /**
     * Get the number of {@link IAtom}s of this {@link INuclide} present.
     * @param nuclide
     * @return A number greater than or equal to {@code 0}.
     */
    @Override
    public int get(N nuclide);
};
