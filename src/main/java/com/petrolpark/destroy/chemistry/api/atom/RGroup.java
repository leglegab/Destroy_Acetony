package com.petrolpark.destroy.chemistry.api.atom;

import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;

/**
 * Not a real {@link IAtom}, but rather exists as a stand-in for other atoms or groups of atoms.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class RGroup implements IAtom<RGroup.Nuclike> {

    public static final Nuclike R_GROUP = new Nuclike();

    public int rGroupNumber;

    @Override
    public Nuclike getNuclide() {
        return R_GROUP;
    };

    @Override
    public double getMass() {
        return 0d; // Skip the reference to the "nuclide"
    };

    @Override
    public double getCharge() {
        return 0d;
    };

    @Override
    public float getElectronegativity() {
        return 2.5f; // Same as that of carbon, though strictly this value should never be used for R groups, as they shouldn't be involved in calculating reactions and the like.
    };

    /**
     * Pronounced "noock-like". Obviously R groups do not have an actual associated {@link INuclide nuclide}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    static class Nuclike implements INuclide {

        @Override
        public double getMass() {
            return 0d;
        };

    };
};
