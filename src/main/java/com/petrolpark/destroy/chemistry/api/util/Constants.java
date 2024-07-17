package com.petrolpark.destroy.chemistry.api.util;

public class Constants {
    
    /**
     * Avagadro's Constant.
     */
    public static final double AVAGADRO_CONSTANT = 6.02214076e23d;

    /**
     * The Boltzman Constant, in joules per kelvin.
     */
    public static final double BOLTZMAN_CONSTANT = 1.380649e-23d;

    /**
     * The Ideal Gas Constant, in joules per kelvin per mole.
     */
    public static final double GAS_CONSTANT = AVAGADRO_CONSTANT * BOLTZMAN_CONSTANT;
};
