package com.petrolpark.destroy.chemistry.api.organic;

import com.petrolpark.destroy.chemistry.api.species.ISpecies;

/**
 * A {@link ISpecies}, particularly a {@link IFunctionalGroupInstance part of that Species} being used to {@link IOrganicReactionGenerator generate an organic} {@link IReaction}.
 * <p>A particular {@link ISpecies} may produce multiple {@link IOrganicReactant}s (each pertaining to a different {@link IFunctionalGroupInstance} that {@link ISpecies} has).</p>
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see OrganicReactant Default implementation
 */
public interface IOrganicReactant<G extends IFunctionalGroup<? super G>> {
    
    /**
     * The {@link ISpecies} of this {@link IOrganicReactant}.
     * @return A {@link ISpecies} containing the {@link IAtom}s of the {@link IOrganicReactant#getGroup() Group}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public ISpecies getSpecies();

    /**
     * The {@link IFunctionalGroupInstance} of this {@link IOrganicReactant}.
     * @return A {@link IFunctionalGroupInstance} whose {@link IAtom}s are all contained in this {@link IOrganicReactant#getSpecies() species}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public IFunctionalGroupInstance<G> getGroup();

    /**
     * Default implementation of {@link IOrganicReactant}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public static record OrganicReactant<G extends IFunctionalGroup<? super G>>(ISpecies species, IFunctionalGroupInstance<G> group) implements IOrganicReactant<G> {

        /**
         * {@inheritDocs}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        @Override
        public ISpecies getSpecies() {
            return species();
        };

        /**
         * {@inheritDocs}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        @Override
        public IFunctionalGroupInstance<G> getGroup() {
            return group();
        };

    };
};
