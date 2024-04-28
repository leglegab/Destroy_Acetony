package com.petrolpark.destroy.chemistry.api.property;

/**
 * An object of which instances have a temperature.
 * @since 1.0
 * @author petrolpark
 */
public interface ITemperature {
    
    /**
     * Get the temperature of this object in kelvins.
     * @return A double which should be <b>strictly greater than {@code 0d}</b>
     */
    public double getTemperature();
};
