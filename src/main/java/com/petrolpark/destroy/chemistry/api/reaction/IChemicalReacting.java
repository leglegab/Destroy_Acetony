package com.petrolpark.destroy.chemistry.api.reaction;

import com.petrolpark.destroy.chemistry.api.mixture.IMixture;
import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.property.ITemperature;

/**
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IChemicalReacting extends IReacting<IChemicalReacting>, ITemperature, IMixture<IMixtureComponent> {
    
};
