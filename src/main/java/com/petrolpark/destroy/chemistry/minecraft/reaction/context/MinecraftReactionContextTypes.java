package com.petrolpark.destroy.chemistry.minecraft.reaction.context;

import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextType;
import com.petrolpark.destroy.chemistry.api.reaction.context.PrimitiveDoubleReactionContext;

public class MinecraftReactionContextTypes {
    
    /**
     * A rudementary approximation of the effect on UV on {@link com.petrolpark.destroy.chemistry.api.reaction.IReaction Reactions} which approximates it as a single intensity of ambiguous wavelength light being supplied.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public static final PrimitiveDoubleReactionContext.Type SIMPLE_UV_POWER = new PrimitiveDoubleReactionContext.Type(UVPowerReactionContext::new, 0d);

    /**
     * @see MinecraftReactionContextTypes#SIMPLE_UV_POWER
     */
    protected static class UVPowerReactionContext extends PrimitiveDoubleReactionContext {

        protected UVPowerReactionContext(double value) {
            super(value);
        };

        @Override
        public IReactionContextType<PrimitiveDoubleReactionContext> getReactionContextType() {
            return SIMPLE_UV_POWER;
        };

    };
};
