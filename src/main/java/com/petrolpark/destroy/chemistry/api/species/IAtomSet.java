package com.petrolpark.destroy.chemistry.api.species;

import java.util.Set;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;

/**
 * A collection of {@link IAtom}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IAtomSet<A extends IAtom<? extends N>, N extends INuclide> {

    /**
     * Get every {@link IAtom} in this {@link ISpecies}.
     * @return A {@link Set} which shouldn't be mutable, containing every {@link IAtom} that contributes to the {@link IRelativeAtomicMass mass} and chemical behaviours of this {@link ISpecies}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public Set<? extends A> getAllAtoms();

    /**
     * Get the {@link IMolecularFormula} of this {@link IAtomSet}.
     * @return A {@link IMolecularFormula} mapping {@link INuclide}s to the number of times {@link IAtom}s {@link IAtom#getNuclide() with} that {@link INuclide} occur in this {@link IAtomSet},
     * or {@code 0} if this contains none.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public IMolecularFormula<N> getMolecularFormula();
};
