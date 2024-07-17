package com.petrolpark.destroy.chemistry.api.mixture;

import com.petrolpark.destroy.chemistry.api.reaction.IReacting;
import com.petrolpark.destroy.chemistry.api.reaction.ITransformation;

/**
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IReactingMultiPhaseMixture <
    P extends IReactingPhase<? super R, ? super T, ? super C>,
    R extends IReactingMultiPhaseMixture<? super P, ? super R, ? super T, ? super C>,
    T extends ITransformation<? super T, ? extends R>,
    C extends IMixtureComponent
> extends
    IReacting<R, T>,
    IMultiPhaseMixture<P, C>
{
    
};
