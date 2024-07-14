package com.petrolpark.destroy.chemistry.api.reaction.context;

import com.petrolpark.destroy.chemistry.minecraft.reaction.context.MinecraftReactionContextTypes;

/**
 * Implementation of {@link IReactionContext} which just wraps a {@code double} primitive.
 * @since Destroy 1.0
 * @author petrolpark
 * @see SimpleValueReactionContext Wrapping an object
 * @see MinecraftReactionContextTypes#UV_POWER Example usage
 */
public abstract class PrimitiveDoubleReactionContext implements IReactionContext<PrimitiveDoubleReactionContext> {
    
    protected final double value;

    protected PrimitiveDoubleReactionContext(double value) {
        this.value = value;
    };

    public double get() {
        return value;
    };

    public static class Type implements IReactionContextType<PrimitiveDoubleReactionContext> {

        protected final Factory factory;
        protected final PrimitiveDoubleReactionContext defaultContext;

        public Type(Factory reactionContextFactory, double defaultValue) {
            factory = reactionContextFactory;
            defaultContext = of(defaultValue);
        };

        public PrimitiveDoubleReactionContext of(double value) {
            return factory.create(value);
        };

        @Override
        public PrimitiveDoubleReactionContext getDefault(IReactionContextProvider reactionContextProvider) {
            return defaultContext;
        };
        
    };

    @FunctionalInterface
    public static interface Factory {
        public PrimitiveDoubleReactionContext create(double value);
    };
};
