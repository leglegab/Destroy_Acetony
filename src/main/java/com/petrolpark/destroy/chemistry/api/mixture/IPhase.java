package com.petrolpark.destroy.chemistry.api.mixture;

import com.petrolpark.destroy.chemistry.api.property.IDensity;
import com.petrolpark.destroy.chemistry.api.property.IHeatable;
import com.petrolpark.destroy.chemistry.api.property.IPressure;
import com.petrolpark.destroy.chemistry.api.property.ITemperature;
import com.petrolpark.destroy.chemistry.api.util.Constants;

/**
 * A single homogenous body of {@link IMixtureComponent}s, with an associated {@link ITemperature temperature} and other state functions.
 * <p>A sealed bottle containing water and oil would have three Phases - the water, the oil and the air.
 * Like a {@link IMixture}, Phases are not defined in terms of a specific volume. </p>
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IPhase<C extends IMixtureComponent> extends IMixture<C>, IHeatable, ITemperature, IPressure, IDensity {
    
    /**
     * The average number of discrete objects per liter in this {@link IPhase}, in moles per liter.
     * This will usually just be the sum of all {@link IMixture#getConcentration(IMixtureComponent) concentrations} of relevant {@link IMixtureComponent}s.
     * @return A molar density greater than or equal to {@code 0d}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public double getMolarDensity();

    /**
     * The pressure exerted by this {@link IPhase}, in pascals. The default implementation assumes this {@link IPhase} is an ideal gas.
     * @return A pressure in pascals greater than or equal to {@code 0d}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    @Override
    public default double getPressure() {
        return Constants.GAS_CONSTANT * getTemperature() * getMolarDensity() * 1000d;
    };
};
