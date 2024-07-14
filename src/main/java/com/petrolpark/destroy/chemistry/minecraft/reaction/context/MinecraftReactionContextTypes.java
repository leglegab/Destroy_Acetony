package com.petrolpark.destroy.chemistry.minecraft.reaction.context;

import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextType;
import com.petrolpark.destroy.chemistry.api.reaction.context.PrimitiveDoubleReactionContext;

public class MinecraftReactionContextTypes {
    
    /**
     * A rudementary approximation of the effect on UV on {@link com.petrolpark.destroy.chemistry.api.reaction.IReaction Reactions} which approximates it as a single intensity of ambiguous wavelength light being supplied.
     */
    public static final PrimitiveDoubleReactionContext.Type UV_POWER = new PrimitiveDoubleReactionContext.Type(UVPowerReactionContext::new, 0d);

    protected static class UVPowerReactionContext extends PrimitiveDoubleReactionContext {

        protected UVPowerReactionContext(double value) {
            super(value);
        };

        @Override
        public IReactionContextType<PrimitiveDoubleReactionContext> getReactionContextType() {
            return UV_POWER;
        };

    };
};
