package com.petrolpark.destroy.chemistry.api.species.structure;

import java.util.Collection;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnection;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnections;

/**
 * An {@link IMolecularStructure} to which {@link IAtom}s can be {@link IModifiableMolecularStructure#add(IAtom, IAtom, IAtomConnectionFactory) added},
 * {@link IModifiableMolecularStructure#remove(IAtom) removed}
 * and {@link IModifiableMolecularStructure#connect(IAtom, IAtom, IAtomConnectionFactory) connected to}
 * and {@link IModifiableMolecularStructure#disconnect(IAtom, IAtom, IAtomConnectionFactory) disconnected from} other {@link IAtom}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IModifiableMolecularStructure<S extends IMolecularStructure<A, N, C, CS>, A extends IAtom<? extends N>, N extends INuclide, C extends IAtomConnection<? extends A, ? extends N>, CS extends IAtomConnections<? extends A, ? extends N, ? extends C>> extends IMolecularStructure<A, N, C, CS> {
    
    /**
     * Adds a new {@link IAtom} to this {@link IMolecularStructure}.
     * @param newAtom A new {@link IAtom} not already {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}
     * @param existingAtom An {@link IAtom} {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure} to which {@code newAtom}'s first {@link IAtomConnection} should be made
     * @param connector A function which forms this initial {@link IAtomConnection}, and which should be passed {@code newAtom}, {@code existingAtom} in that order
     * @throws IllegalArgumentException If {@code existingAtom} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}
     * @throws IllegalArgumentException If {@code newAtom} is already {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see IModifiableMolecularStructure#connect(IAtom, IAtom, IAtomConnectionFactory) Connecting two IAtoms already in the IMolecularStructure
     */
    void add(A newAtom, A existingAtom, IAtomConnectionFactory<A, C> connector);

    /**
     * Removes an {@link IAtom} from this {@link IMolecularStructure} and any {@link IAtomConnection}s to it. This mutates this {@link IMolecularStructure}.
     * @param atom The {@link IAtom} to remove
     * @return A {@link Collection} of additional {@link IMolecularStructure}s (not including this) which result from the {@link IAtom} being removed and hence parts of the {@link IMolecularStructure} disconnected.
     * They include the same {@link IAtom} objects this {@link IMolecularStructure} had to begin with.
     * This may be empty (if the {@link IAtom} was terminal) or contain one or more fragment {@link IMolecularStructure}s for which this {@link IAtom} was a connecting branch.
     * Unless specified by a child class, no guarantee is made about which fragment this {@link IModifiableMolecularStructure} becomes and which fragments go in this {@link Collection}.
     * @throws IllegalArgumentException If {@code atom} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    Collection<S> remove(A atom);

    /**
     * Adds an {@link IAtomConnection connection} between two {@link IAtom}s in this {@link IMolecularStructure}.
     * @param atom1
     * @param atom2
     * @param connector A function which forms the {@link IAtomConnection}, which should be passed {@code atom1}, {@code atom2} in that order
     * @throws IllegalArgumentException If either {@code atom1} or {@code atom2} are not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    void connect(A atom1, A atom2, IAtomConnectionFactory<A, C> connector);

    /**
     * Removes the {@link IAtomConnection} between two {@link IAtom}s {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * This results in a mutation of this {@link IModifiableMolecularStructure}.
     * @param atom1
     * @param atom2
     * @return A {@link Collection} of additional {@link IMolecularStructure}s (not including this) which result from the disconnection being made.
     * Typically this will either be empty (if the disconnection was a ring-break or the {@link IAtoms} weren't {@link IMolecularStructure#getConnections(IAtom) connected} to begin with) or contain one other fragment {@link IMolecularStructure}.
     * Unless specified by a child class, no guarantee is made about which fragment this {@link IModifiableMolecularStructure} becomes and which fragment(s) go in this {@link Collection}.
     * @throws IllegalArgumentException If either {@code atom1} or {@code atom2} are not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    Collection<S> disconnect(A atom1, A atom2);

    /**
     * Forges {@link IAtomConnection}s between {@link IAtom}s in an {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    @FunctionalInterface
    public static interface IAtomConnectionFactory<A, C> {

        /**
         * Forge an {@link IAtomConnection} between {@link IAtom}s in an {@link IMolecularStructure}.
         * @param atom1
         * @param atom2
         * @return A {@link IAtomConnection} between {@code atom1} and {@code atom2}
         * @since Destroy 0.1.0
         * @author petrolpark
         */
        public C createConnection(A atom1, A atom2);
    };
};
