package com.petrolpark.destroy.chemistry.api.organic;

import com.petrolpark.destroy.chemistry.api.atom.IAtom;
import com.petrolpark.destroy.chemistry.api.atom.RGroup;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;
import com.petrolpark.destroy.chemistry.api.species.ISpecies;
import com.petrolpark.destroy.chemistry.api.species.NamespacedId;

/**
 * A category of functional groups. For example, "alkenes", "alcohols" would each be separate, individual {@link IFunctionalGroup} instances.
 * <p>This is not to be confused with {@link IFunctionalGroupInstance}s, which are specific to a particular {@link ISpecies} and refer to specific {@link IAtom}s.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IFunctionalGroup<G extends IFunctionalGroup<? super G>> extends IRegisteredChemistryObject<IFunctionalGroup<G>, NamespacedId> {
    
    /**
     * Get an exemplary {@link ISpecies} (likely containing {@link RGroup}s) with this {@link IFunctionalGroup} and ideally no others.
     * @since Destroy 0.1.0
     * @author petrolpark
     * @return An {@link ISpecies} which <strong>must</strong> contain an {@link IFunctionalGroupInstance instance} of this {@link IFunctionalGroup}.
     * //TODO link to functional group finders
     */
    public ISpecies getGeneralSpecies();
};
