package com.petrolpark.destroy.chemistry.api.mixture;

/**
 * In the context of Destroy, {@link IState} refers to the physical properties of a {@link IPhase}. Many {@link IPhase}s will have the same {@link IState}.
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see State Default implementation
 */
public interface IState {
    
    /**
     * Whether this {@link IState} has a definite volume and shape, and contained particles have no independent free motion.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public boolean isSolid();

    /**
     * Whether this {@link IState} is mostly incompressible, with a constant, pressure-independent volume, but with free-moving particles.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public boolean isLiquid();

    /**
     * Whether this {@link IState} is a compressible {@link IState#isFluid() fluid}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public boolean isGas();

    /**
     * Whether this {@link IState} contains free-moving particles.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public default boolean isFluid() {
        return isLiquid() || isGas();
    };

    /**
     * The default implementation of {@link IState} in Destroy.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public static enum State implements IState {

        SOLID, LIQUID, GAS;

        /**
         * {@inheritDoc}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        @Override
        public boolean isSolid() {
            return this == SOLID;
        };

        /**
         * {@inheritDoc}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        @Override
        public boolean isLiquid() {
            return this == LIQUID;
        };

        /**
         * {@inheritDoc}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        @Override
        public boolean isGas() {
            return this == GAS;
        };
    };
};
