package com.petrolpark.destroy.chemistry.api.species.tag;

import java.util.Collection;

import com.petrolpark.destroy.chemistry.api.species.ISpecies;
import com.petrolpark.destroy.chemistry.api.tag.ICompleteTag;
import com.petrolpark.destroy.chemistry.api.tag.ITag;
import com.petrolpark.destroy.chemistry.api.species.IRegisteredSpecies;

/**
 * A {@link ISpeciesTag} which keeps a record of every {@link IRegisteredSpecies registered} {@link ISpecies} with that {@link ITag}.
 * There may be unregistered {@link ISpecies} which do still have the {@link ITag}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IRegisteredSpeciesTag extends ISpeciesTag<IRegisteredSpecies>, ICompleteTag<IRegisteredSpecies> {
    
    /**
     * {@inheritDocs}
     * Get every {@link IRegisteredSpecies} with this {@link ISpeciesTag}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    @Override
    public Collection<IRegisteredSpecies> getAll();
};
