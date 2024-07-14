package com.petrolpark.destroy.chemistry.api.species;

import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.property.ICharge;
import com.petrolpark.destroy.chemistry.api.property.INamespace;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;

/**
 * At the end of the day a Species is a collection of {@link IAtom}s.
 * This includes things like molecules, ions and free atoms, but typically not things like enzymes.
 * @since Destroy 1.0
 * @author petrolpark
 * @see ISpeciesComparator Checking if two Species are the same
 */
public interface ISpecies extends IRelativeAtomicMass, ICharge, IMixtureComponent, INamespace {
    
};
