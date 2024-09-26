package com.petrolpark.destroy.chemistry.api.species.structure;

import java.util.Collection;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.species.IAtomSet;

/**
 * A connected map of {@link IAtom}s and {@link IAtomConnection bonds}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IMolecularStructure<A extends IAtom<? extends N>, N extends INuclide, C extends IMolecularStructure.IAtomConnection<? extends A, ? extends N>, CS extends IMolecularStructure.IAtomConnections<? extends A, ? extends N, ? extends C>> extends IAtomSet<A, N> {

    /**
     * Whether this {@link IMolecularStructure} contains the given {@link IAtom}.
     * @param atom
     * @return {@code true} if this {@link IAtom} can be found in this {@link IMolecularStructure} (usually meaning it has at least one {@link IMolecularStructure#getConnections(IAtom) connection} to another {@link IAtom} in this {@link IMolecularStructure}).
     * @since Destroy 0.1.0 
     * @author petrolpark
     */
    public boolean contains(A atom);
    
    /**
     * Get the {@link IAtomConnections} this {@link IAtom} has in this {@link IMolecularStructure}.
     * @param atom
     * @return A non-{@code null} {@link Collection} of {@link IAtomConnection}s.
     * @throws IllegalArgumentException If the {@link IAtom} is not {@link IMolecularStructure#contains(IAtom) a part} of this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public CS getConnections(A atom);

    /**
     * Each {@link IAtomConnection} involving an {@link IAtom}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public interface IAtomConnections<A extends IAtom<? extends N>, N extends INuclide, C extends IAtomConnection<? extends A, ? extends N>> extends Collection<C> {};

    /**
     * An undirected joining of two {@link IAtom}s in a {@link IMolecularStructure}.
     * This may be a traditional "bond" between two adjacent {@link IAtom}s, or even two far-away {@link IAtom}s joined in some way by a pi system.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public interface IAtomConnection<A extends IAtom<? extends N>, N extends INuclide> {};
};
