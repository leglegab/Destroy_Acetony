package com.petrolpark.destroy.chemistry.api.reaction;

import com.petrolpark.destroy.chemistry.api.mixture.IMixture;
import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.property.ITemperature;
import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextProvider;

public interface IChemicalReacting extends IReacting<IChemicalReacting, IChemicalReaction>, ITemperature, IMixture<IMixtureComponent>, IReactionContextProvider {
    
};
