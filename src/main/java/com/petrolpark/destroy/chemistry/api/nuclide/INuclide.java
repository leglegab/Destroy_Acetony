package com.petrolpark.destroy.chemistry.api.nuclide;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;

/**
 * A "type" of {@link IAtom}. All atoms have one (and only one) associated {@link INuclide}.
 * There can exist many atoms of the same {@link INuclide}. Different {@link INuclide}s of the same {@link IElement} are usually distinguished by their neutron number.
 * @since Destroy 1.0
 * @author petrolpark
 * @see ElementAveragedNuclide Default implementation
 */
public interface INuclide extends IRelativeAtomicMass {
    
    /**
     * Two different {@link INuclide}s of the same {@link IElement} should give the same {@link IElement} object.
     * @since Destroy 1.0
     * @return 
     */
    public IElement getElement();
};
