package com.petrolpark.destroy.chemistry.api.property;

/**
 * An object of which instances have an associated relative atomic mass (which can be 0).
 * @since 1.0
 * @author petrolpark
 */
public interface IRelativeAtomicMass {
    
    /**
     * Get the relative atomic mass of whatever this is, in grams per mole.
     * This can be 0, so the proper precautions should be taken for that.
     * @return A usually positive double
     */
    public double getMass();
};
