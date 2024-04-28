package com.petrolpark.destroy.chemistry.api.mixture;

/**
 * A collection of {@link IMixtureComponent}s, (mainly {@link ISpecies} like molecules), which each have a concentration.
 * If you look on Wikipedia you'll see that a mixture is "a material made up of two or more chemical substances which are not bonded", and I'd say that's fairly accurate.
 * @since 1.0
 * @author petrolpark
 */
public interface IMixture<C extends IMixtureComponent> {
    
    /**
     * Get the concentration (in moles per litre) of the given {@link IMixtureComponent} (or any component joined by {@link Object#equals(Object)}) in this {@link IMixture}.
     * @param component
     * @return A value which must be greater than or equal to {@code 0d}.
     */
    public double getConcentrationOf(C component);
};
