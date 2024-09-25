package com.petrolpark.destroy.chemistry.api.species.structure;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnection;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnections;

/**
 * An {@link IMolecularStructure} which defines a {@link ITraversableMolecularStructure#getSelectedAtom() "selected"} {@link IAtom}.
 * This {@link IAtom} isn't special in any way, but just provides utility for {@link IModifiableTraversableMolecularStructure modifying} {@link IMolecularStructure}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface ITraversableMolecularStructure<A extends IAtom<? extends N>, N extends INuclide, C extends IAtomConnection<? extends A, ? extends N>, CS extends IAtomConnections<? extends A, ? extends N, ? extends C>> extends IMolecularStructure<A, N, C, CS> {
    
    /**
     * A non-{@code null} {@link IAtom} which exists in this {@link IMolecularStructure}.
     * This {@link IAtom} has no special bearing on the behaviour of this {@link IMolecularStructure}, and just exists as a utility for exploring and modifying {@link IMolecularStructure}s in a convenient way.
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see ITraversableMolecularStructure#select(IAtom) Changing the selected IAtom
     */
    A getSelectedAtom();

    /**
     * Change the {@link ITraversableMolecularStructure#getSelectedAtom() selected} {@link IAtom} to the one given.
     * If that {@link IAtom} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}, the {@link ITraversableMolecularStructure#getSelectedAtom() selected} {@link IAtom} does not change.
     * @param atom
     * @throws IllegalArgumentException If that {@link IAtom} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public void select(A atom);

    /**
     * Get the {@link IMolecularStructure#getConnections(IAtom) connections} to the {@link ITraversableMolecularStructure#getSelectedAtom() selected} {@link IAtom}.
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see IMolecularStructure#getConnections(IAtom) Getting the connections of an arbitrary IAtom
     */
    public default CS getConnections() {
        return getConnections(getSelectedAtom());
    };
};
