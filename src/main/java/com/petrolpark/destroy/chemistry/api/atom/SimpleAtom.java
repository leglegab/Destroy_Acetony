package com.petrolpark.destroy.chemistry.api.atom;

import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.property.IElectronegativity;

/**
 * A simplistic implementation of {@link IAtom}.
 * Some simplifications used:
 * <ul>
 * <li>No distinction is made between atoms in different energy states (neither electronic nor nuclear).</li>
 * <li>The formal charge of an atom is its actual charge (for the purposes of {@link com.petrolpark.destroy.chemistry.api.property.ICharge ICharge})</li>
 * <li>All atoms of one {@link INuclide} have the same electronegativity.
 * </ul>
 * This is the default implentation of {@link IAtom} in Destroy.
 * @since 1.0
 * @author petrolpark
 */
public class SimpleAtom<N extends INuclide & IElectronegativity> implements IAtom<N> {

    private final N nuclide;
    private short charge;

    public SimpleAtom(N nuclide) {
        this.nuclide = nuclide;
        charge = 0;
    };

    public static <Nu extends INuclide & IElectronegativity> SimpleAtom<Nu> create(Nu nuclide) {
        return new SimpleAtom<>(nuclide);
    };

    @Override
    public double getCharge() {
        return charge;
    };

    @Override
    public N getNuclide() {
        return nuclide;
    };

    public float getElectronegativity() {
        return nuclide.getElectronegativity();
    };
    
};
