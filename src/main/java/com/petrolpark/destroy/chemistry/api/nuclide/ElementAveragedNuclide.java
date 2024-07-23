package com.petrolpark.destroy.chemistry.api.nuclide;

import com.petrolpark.destroy.chemistry.api.property.IElectronegativity;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;

/**
 * An simplistic implementation of {@link INuclide} which represents a weighted "average" of all actual nuclides of an element.
 * Some simplifications used:
 * <ul>
 * <li>All nuclides of an element have a mass equal to the Earth average for that element</li>
 * </ul>
 * This is the default implementation of {@link INuclide} in Destroy, across which isotopic effects are ignored.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class ElementAveragedNuclide implements INuclide, IElement, IRegisteredChemistryObject<ElementAveragedNuclide, String>, IElectronegativity {

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

    public ElementAveragedNuclide(String symbol, float relativeAtomicMass, float electronegativity) {
        this.symbol = symbol;
        this.relativeAtomicMass = relativeAtomicMass;
        this.electronegativity = electronegativity;
    };

    @Override
    public String getId() {
        return symbol;
    };

    @Override
    public IElement getElement() {
        return this;
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
    public String toString() {
        return "Element[" + symbol + "]";
    };
    
};
