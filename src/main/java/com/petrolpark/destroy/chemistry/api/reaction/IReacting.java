package com.petrolpark.destroy.chemistry.api.reaction;

import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextProvider;
import com.petrolpark.destroy.chemistry.api.reactor.IReactor;
import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.mixture.IMixture;

/**
 * Something in which {@link IReaction}s occur, typically a {@link IMixture}.
 * In contrast to an {@link IReactor}, which represents the thing in which the chemical change is occuring, {@link IReacting}s represent the substance undergoing change, like a {@link IMixture}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IReacting extends IReactionContextProvider {
    
    /**
     * Simulate the {@link IReaction} of {@link IMixtureComponent species} for a short while.
     */
    public void react();

    /**
     * Whether any {@link IReaction}s are currently occuring
     * - equivalently, whether calling {@link IReacting#react()} will result in a change in state.
     */
    public boolean isAtEquilibrium();
};
