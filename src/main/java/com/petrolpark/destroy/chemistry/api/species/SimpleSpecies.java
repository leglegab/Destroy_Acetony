package com.petrolpark.destroy.chemistry.api.species;

import java.util.HashSet;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.property.IRelativeAtomicMass;
import com.petrolpark.destroy.chemistry.api.species.tag.ISpeciesTag;
import com.petrolpark.destroy.chemistry.api.tag.ITag;

/**
 * A simplistic implementation of {@link ISpecies}.
 * Chemical assumptions made include:
 * <ul>
 * <li> No change in {@link IRelativeAtomMass mass} comes about as a result of the bonding between {@link IAtom}s - that is, the {@link SimpleSpecies#getMass() mass} of this {@link ISpecies} is just the sum of the {@link IRelativeAtomicMass masses} of {@link SimpleSpecies#getAllAtoms() its} {@link IAtom}s.
 * </ul>
 * <p>Technical restrictions are:</p>
 * <ul>
 * <li>Only {@link ISpeciesTag}s are stored, even though {@link ITag}s of different types may be applicable
 * </ul>
 * <p>{@link SimpleSpecies} are not mutable.</p>
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public abstract class SimpleSpecies implements ISpecies {

    /**
     * The relative atomic mass (in grams per mole) of this {@link SimpleSpecies}, which is just the sum of {@link SimpleSpecies#getAllAtoms() all} {@link IAtom}s' {@link IAtom#getMass() masses}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    protected final double relativeAtomicMass;

    public SimpleSpecies(double relativeAtomicMass, short charge, HashSet<ISpeciesTag<? super ISpecies>> tags) {
        this.relativeAtomicMass = relativeAtomicMass;
        this.charge = charge;
        this.tags = tags;
        throw new UnsupportedOperationException(); //TODO auto generated method stump
    }

    /**
     * The total charge (in elementary charges) of this {@link SimpleSpeces}, which is just the sum of {@link SimpleSpecies#getAllAtoms() all} {@link IAtom}s' {@link IAtom#getCharge() charges}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    protected final short charge;

    /**
     * All {@link ISpeciesTag}s which apply to this {@link SimpleSpecies}.
     */
    protected final HashSet<ISpeciesTag<? super ISpecies>> tags;

    @Override
    public double getMass() {
        return relativeAtomicMass;
    };

    @Override
    public double getCharge() {
        return charge;
    };

    @Override
    public <T extends ITag<? extends ISpecies>> boolean hasTag(T tag) {
        return tags.contains(tag);
    };

    
};
