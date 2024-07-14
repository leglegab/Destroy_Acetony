package com.petrolpark.destroy.chemistry.api.mixture;

import com.petrolpark.destroy.chemistry.api.species.ISpecies;

/**
 * A collection of {@link IMixtureComponent}s, (mainly {@link ISpecies} like molecules), which each have a concentration.
 * If you look on Wikipedia you'll see that a mixture is "a material made up of two or more chemical substances which are not bonded", and I'd say that's fairly accurate.
 * <p>A Mixture is not defined in terms of a volume - for example, 100ml of one solution and 200ml of a solution  in a different beaker with the exact same concentrations are the same Mixture, as it is defined here.</p>
 * <p>Mixtures also make no guarantee about homogeneity (unlike {@link IPhase}s). Consequently the {@link IMixture#getConcentration(IMixtureComponent component) returned concentration} of a {@link IMixtureComponent} may be an average for the whole Mixture.</p>
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IMixture {
    
    /**
     * Get the concentration (in moles per litre) of the given {@link IMixtureComponent} (or any component joined by {@link Object#equals(Object) equivalence}) in this {@link IMixture}.
     * <p>As {@link IMixture Mixtures make no guarantee of homogeneity}, this value may be an average.
     * @param component
     * @return A value greater than or equal to {@code 0d}, in moles per liter.
     */
    public double getConcentration(IMixtureComponent component);
};
