package com.petrolpark.destroy.chemistry.api.nuclide;

import com.petrolpark.destroy.chemistry.api.property.IElectronegativity;
import com.petrolpark.destroy.chemistry.api.property.IStandardValenciesForFROWNS;
import com.petrolpark.destroy.chemistry.api.registry.IChemistryRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;

/**
 * An simplistic implementation of {@link INuclide} which represents a weighted "average" of all actual nuclides of an element.
 * Some simplifications used:
 * <ul>
 * <li>All nuclides of an element have a mass equal to the Earth average for that element</li>
 * </ul>
 * This is the default implementation of {@link INuclide} in Destroy, across which isotopic effects are ignored.
 * @since 1.0
 * @author petrolpark
 */
public class ElementAveragedNuclide implements INuclide, IRegisteredChemistryObject<ElementAveragedNuclide, String>, IElectronegativity, IStandardValenciesForFROWNS {

    /**
     * The IUPAC symbol for this element
     */
    public final String symbol;
    /**
     * The weighted average relative atomic mass of this element on Earth.
     */
    public final float relativeAtomicMass;
    /**
     * The Pauling electronegativity value for this element.
     */
    public final float electronegativity;
    /**
     * The usual numbers of "bonds" (single bond equivalents) {@link IAtom}s of this element have.
     */
    public final float[] valencies;

    public ElementAveragedNuclide(String symbol, float relativeAtomicMass, float electronegativity, float[] valencies) {
        this.symbol = symbol;
        this.relativeAtomicMass = relativeAtomicMass;
        this.electronegativity = electronegativity;
        this.valencies = valencies;
    };

    @Override
    public String getId() {
        return symbol;
    };

    @Override
    public IChemistryRegistry<ElementAveragedNuclide, String> getRegistry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegistry'");
    };

    @Override
    public double getMass() {
        return relativeAtomicMass;
    };

    @Override
    public float getElectronegativity() {
        return electronegativity;
    };

    @Override
    public float getNextLowestValency(float valency) {
        for (float validValency : valencies) if (validValency >= valency) return validValency;
        return 0f;
    };

    @Override
    public String toString() {
        return "Element[" + symbol + "]";
    };
    
};
