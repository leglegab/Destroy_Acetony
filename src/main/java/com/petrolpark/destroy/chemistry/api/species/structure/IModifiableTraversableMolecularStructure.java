package com.petrolpark.destroy.chemistry.api.species.structure;

import java.util.Collection;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.nuclide.INuclide;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnection;
import com.petrolpark.destroy.chemistry.api.species.structure.IMolecularStructure.IAtomConnections;

/**
 * 
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IModifiableTraversableMolecularStructure<S extends IModifiableTraversableMolecularStructure<? super S, A, N, C, CS>, A extends IAtom<? extends N>, N extends INuclide, C extends IAtomConnection<? extends A, ? extends N>, CS extends IAtomConnections<? extends A, ? extends N, ? extends C>> extends ITraversableMolecularStructure<A, N, C, CS>, IModifiableMolecularStructure<S, A, N, C, CS> {
    
    /**
     * Adds a new {@link IAtom} to this {@link IMolecularStructure} connected to the {@link ITraversableMolecularStructure#getSelectedAtom() currently selected} {@link IAtom}
     * @param atom A new {@link IAtom} not already {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}
     * @param connector A function which forms this initial {@link IAtomConnection}, and which should be passed {@code newAtom}, {@code existingAtom} in that order
     * @throws IllegalArgumentException If {@code atom} is already {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see IModifiableTraversableMolecularStructure#connect(IAtom, IAtomConnectionFactory) Connecting to an IAtom already in the IMolecularStructure
     */
    void add(A atom, IAtomConnectionFactory<A, C> connector);

    /**
     * Removes an {@link IAtom} from this {@link IMolecularStructure} and any {@link IAtomConnection}s to it. This mutates this {@link IMolecularStructure}.
     * If the removed {@link IAtom} is {@link ITraversableMolecularStructure#getSelectedAtom() currently selected}, a new one is arbitrarily {@link ITraversableMolecularStructure#select(IAtom) reselected}.
     * @param atom The {@link IAtom} to remove
     * @return A {@link Collection} of additional {@link IMolecularStructure}s (not including this) which result from the atom being removed and hence parts of the {@link IMolecularStructure} disconnected.
     * They include the same {@link IAtom} objects this {@link IMolecularStructure} had to begin with.
     * This may be empty (if the {@link IAtom} was terminal) or contain one or more fragment {@link IMolecularStructure}s for which this {@link IAtom} was a connecting branch.
     * This {@link IModifiableTraversableMolecularStructure} will still contain the {@link ITraversableMolecularStructure#getSelectedAtom() currently selected} {@link IAtom}, and the fragments in this {@link Collection} will have their {@link ITraversableMolecularStructure#select(IAtom) selected IAtom} set to one of the ones that was originally connected to {@code atom}.
     * This selection is arbitrary, so if the fragments need further modification their {@link ITraversableMolecularStructure#getSelectedAtom() currently selected} {@link IAtom} should be {@link ITraversableMolecularStructure#select(IAtom) reselected.
     * @throws IllegalArgumentException If {@code atom} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    @Override
    public Collection<S> remove(A atom);
    
    /**
     * Adds an {@link IAtomConnection connection} between the {@link ITraversableMolecularStructure#getSelectedAtom() currently selected} {@link IAtom} and another in this {@link IMolecularStructure}.
     * @param atom
     * @param connector A function which forms the {@link IAtomConnection}, which should be passed the {@link ITraversableMolecularStructure#getSelectedAtom() currently selected} {@link IAtom}, then {@code atom} in that order
     * @throws IllegalArgumentException If {@code atom} or {@code atom2} is not {@link IMolecularStructure#contains(IAtom) in} this {@link IMolecularStructure}.
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see IModifiableMolecularStructure#connect(IAtom, IAtom, com.petrolpark.destroy.chemistry.api.species.structure.IModifiableMolecularStructure.IAtomConnectionFactory) Connecting arbitrary IAtoms
     */
    default void connect(A atom, IAtomConnectionFactory<A, C> connector) {
        connect(getSelectedAtom(), atom, connector);
    };
};
