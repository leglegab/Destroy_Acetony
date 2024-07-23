package com.petrolpark.destroy.chemistry.api.registry;

/**
 * A chemistry-related object which has an associated ID.
 * Instances of this type are usually registered to a {@link IChemistryRegistry}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IRegisteredChemistryObject<T extends IRegisteredChemistryObject<T, ID>, ID> {

    /**
     * A unique identifier for this object.
     * This should be totally unique for any two objects which are not {@link Object#equals(Object) equal}. 
     * @return A unique indentifying object, often a {@link String}
     * @since Destroy 1.0
     * @author petrolpark
     */
    public ID getId();

};
