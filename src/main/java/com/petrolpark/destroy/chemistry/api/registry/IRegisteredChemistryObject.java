package com.petrolpark.destroy.chemistry.api.registry;

/**
 * A chemistry-related object which has an associated ID.
 * Instances of this type are usually registered to a {@link IChemistryRegistry}.
 * @since 1.0
 * @author petrolpark
 */
public interface IRegisteredChemistryObject<T extends IRegisteredChemistryObject<T, ID>, ID> {

    /**
     * A unique identifier for this object.
     * This should be totally unique for any two objects which are not {@link Object#equals(Object) equal}. 
     * @return A unique indentifying object, often a {@link String}
     */
    public ID getId();

    /**
     * Get the {@link IChemistryRegistry} to which this object would usually be registered.
     * It is not always guaranteed that it is - it is possible to check with {@link IRegisteredChemistryObject#isRegistered()}.
     * @return The {@link IChemistryRegistry} to which this object <em>would</em> be registered
     */
    public IChemistryRegistry<T, ID> getRegistry();

    @SuppressWarnings("unchecked")
    public default boolean isRegistered() {
        return getRegistry().isRegistered((T)this);
    };

};
