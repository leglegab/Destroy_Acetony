package com.petrolpark.destroy.chemistry.api.species.tag;

import com.petrolpark.destroy.chemistry.api.species.ISpecies;
import com.petrolpark.destroy.chemistry.api.tag.ITag;

/**
 * A {@link ITag} applicable to {@link ISpecies}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface ISpeciesTag<O extends ISpecies> extends ITag<O> {
    
};
