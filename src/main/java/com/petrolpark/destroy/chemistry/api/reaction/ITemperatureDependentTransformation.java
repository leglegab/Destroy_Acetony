package com.petrolpark.destroy.chemistry.api.reaction;

import com.petrolpark.destroy.chemistry.api.property.ITemperature;

/**
 * A {@link ITransformation} which occurs at a {@link IRateTransformation#getRate(IReacting) rate} depending on the {@link ITemperature} of its {@link IReacting system}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface ITemperatureDependentTransformation <
    T extends ITemperatureDependentTransformation<? super T, ? super R>,
    R extends
        IReacting<? super R, ? extends T>
        & ITemperature
> extends IRateTransformation<T, R> {

};
