package com.petrolpark.destroy.chemistry.api.registry;

import javax.annotation.Nullable;

/**
 * An object which indexes chemistry-related objects.
 * @since Destroy 1.0
 * @author petrolpark
 * @see HashMapRegistry Default implementation
 */
public interface IChemistryRegistry<T extends IRegisteredChemistryObject<T, ID>, ID> {

    /**
     * Add an object to this registry under the object's {@link IRegisteredChemistryObject#getId() id}.
     * Generally objects should not be registered or removed during runtime, and some implementations may raise an error if they are.
     * @param <T> The type of the object
     * @param object An object to store, which should be accessible at any point during runtime
     * @throws IllegalArgumentException If the {@link IRegisteredChemistryObject} has no {@link IRegisteredChemistryObject#getId() id}
     * @since Destroy 1.0
     * @author petrolpark
     */
    public void register(T object) throws IllegalArgumentException;
    
    /**
     * Retrieve an object stored in this registry.
     * @param id The unique identifier for the object
     * @return The object with the given index, or {@code null} if no object with that index exists (an error may also be raised)
     */
    @Nullable
    public T get(ID id);

    /**
     * Whether this {@link IChemistryRegistry} contains a {@link IRegisteredChemistryObject} with the given {@link ID}.
     * @param id
     * @return
     */
    public boolean isRegistered(ID id);

    public boolean isRegistered(T object);

    public void finalizeRegistry();
};
