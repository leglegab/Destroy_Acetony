package com.petrolpark.destroy.chemistry.api.species;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.property.ICharge;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;
import com.petrolpark.destroy.chemistry.api.tag.ITaggable;

/**
 * At the end of the day an {@link ISpecies} is a collection of {@link IAtom}s.
 * This includes things like molecules, ions and free atoms, but typically not things like enzymes.
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see ISpeciesComparator Checking if two Species are the same
 * @see SimpleSpecies Default implementation
 */
public interface ISpecies extends IRelativeAtomicMass, ICharge, IMixtureComponent, ITaggable<ISpecies>, IAtomSet<IAtom<INuclide>, INuclide> {
    

};
