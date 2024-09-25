package com.petrolpark.destroy.chemistry.api.nuclide;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;

/**
 * The group of {@link IAtom}s with (roughly) identical chemical properties.
 * Two different {@link IAtom}s may have the same {@link IElement} but different {@link INuclide}s (in which case they are probably isotopes).
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IElement {
    
};
