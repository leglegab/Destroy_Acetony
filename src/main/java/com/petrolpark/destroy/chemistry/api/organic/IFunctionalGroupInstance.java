package com.petrolpark.destroy.chemistry.api.organic;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.species.IAtomSet;
import com.petrolpark.destroy.chemistry.api.species.ISpecies;

/**
 * A particular occurance of a particular {@link IFunctionalGroup} in an {@link ISpecies}. Different {@link IFunctionalGroupInstance}s may contain the same {@link IAtom}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IFunctionalGroupInstance<G extends IFunctionalGroup<? super G>> extends IAtomSet<IAtom<INuclide>, INuclide> {
    
    /**
     * Get the {@link IFunctionalGroup} of which this is an instance.
     * @return A universal {@link IFunctionalGroup} common to all instances of it
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public G getFunctionalGroup();
};
