package com.petrolpark.destroy.chemistry.api.mixture;

import com.petrolpark.destroy.chemistry.api.property.IHeatable;
import com.petrolpark.destroy.chemistry.api.property.ITemperature;

/**
 * A single homogenous body of {@link IMixtureComponent}s, with an associated {@link ITemperature temperature} and other state functions.
 * <p>A sealed bottle containing water and oil would have three Phases - the water, the oil and the air.
 * Like a {@link IMixture}, Phases are not defined in terms of a specific volume. </p>
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IPhase extends IMixture, ITemperature, IHeatable {
    
};
