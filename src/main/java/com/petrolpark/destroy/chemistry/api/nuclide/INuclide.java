package com.petrolpark.destroy.chemistry.api.nuclide;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;

/**
 * A "type" of {@link IAtom}. All atoms have one (and only one) associated Nuclide.
 * There can exist many atoms of the same Nuclide.
 * @since 1.0
 * @author petrolpark
 */
public interface INuclide extends IRelativeAtomicMass {
    
};
