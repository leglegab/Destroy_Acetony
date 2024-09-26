package com.petrolpark.destroy.chemistry.api.mixture;

import java.util.Collection;

/**
 * A {@link IMixture} consisting of multiple {@link IPhase}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IMultiPhaseMixture<P extends IPhase<? super C>, C extends IMixtureComponent> extends IMixture<C>, Collection<P> {
    
};
