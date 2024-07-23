package com.petrolpark.destroy.chemistry.api.mixture;

import java.util.Collection;

import com.petrolpark.destroy.chemistry.api.reaction.IReaction;
import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContext;

/**
 * While it is possible for {@link IReacting} to calculate their possible {@link IReaction}s every tick,
 * this is obviously hugely costly. {@link IPossibleReactionManager}s are essentially a cache of possible
 * {@link IReaction}s that can be undergone.
 * <p>They are not required, but obviously useful.</p>
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IPossibleReactionManager {
    
    /**
     * Get the possible {@link IReaction}s which can progress.
     * @return A collection of {@link IReaction}, which, if they progress right now, are likely not to have a {@link IReaction#getRate(IMixture, com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextProvider) rate} of {@code 0d}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Collection<IReaction> getPossibleReactions();

    /**
     * Refresh all possible {@link IReaction}s to reflect the fact that a brand new {@link IMixtureComponent} has just been added.
     * It is assumed that this {@link IMixtureComponent} has been checked to verify that it does not already exist in this {@link IMixture}.
     * @param component
     */
    public void componentAdded(IMixtureComponent component);

    public void componentRemoved(IMixtureComponent component);

    public void reactionContextChanged(IReactionContext<?> newContext);
};
