package com.petrolpark.destroy.chemistry.api.mixture;

import com.petrolpark.destroy.chemistry.api.reaction.IReacting;
import com.petrolpark.destroy.chemistry.api.reaction.ITransformation;
import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextProvider;

public interface IReactingPhase <
    R extends IReactingPhase<? super R, ? super T, ? super C>,
    T extends ITransformation<? super T, ? extends R>,
    C extends IMixtureComponent
> extends
    IPhase<C>,
    IReacting<R, T>,
    IReactionContextProvider
{
    
};
