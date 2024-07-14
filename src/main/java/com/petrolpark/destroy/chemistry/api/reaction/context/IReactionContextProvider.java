package com.petrolpark.destroy.chemistry.api.reaction.context;

import java.util.Optional;

/**
 * Something which can provide additional information for {@link com.petrolpark.destroy.chemistry.api.reaction.IReaction Reactions}, such as UV power and possible soluble solids.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IReactionContextProvider {

    /**
     * 
     * @param <RC>
     * @param reactionContextType
     * @return A non-{@code null} {@link IReactionContext}, possibly the {@link IReactionContextType#getDefault() default}
     */
    public default <RC extends IReactionContext<RC>> RC getReactionContext(IReactionContextType<RC> reactionContextType) {
        return getOptionalReactionContext(reactionContextType).orElse(reactionContextType.getDefault(this));
    };
    
    /**
     * Get an {@link Optional} which should contain the relevant {@link IReactionContext} if it exists.
     * This method should just be internal - {@link IReactionContextProvider#getReactionContext(IReactionContextType)} is the public facing method, which is guaranteed to return a result, even if it just the {@link IReactionContextType#getDefault() default}.
     * @param <RC> The class of the {@link IReactionContext}
     * @param reactionContextType The {@link IReactionContextType type} of the {@link IReactionContext} to check for
     * @return A non-{@code null} {@link Optional}
     * @see IReactionContextProvider#getReactionContext(IReactionContextType) The public-facing version of this method
     */
    <RC extends IReactionContext<RC>> Optional<RC> getOptionalReactionContext(IReactionContextType<RC> reactionContextType);

    /**
     * Whether this {@link IReactionContextProvider} specifies a {@link IReactionContext} of the given {@link IReactionContextType type} that isn't just the {@link IReactionContextType#getDefault() default}.
     * @param reactionContextType The {@link IReactionContextType type} of the {@link IReactionContext} to check for
     * @return {@code true} if a non-{@link IReactionContextType#getDefault() default} {@link IReactionContext} is specified
     */
    public default boolean specifies(IReactionContextType<?> reactionContextType) {
        return getOptionalReactionContext(reactionContextType).isPresent();
    };
};
