package com.petrolpark.destroy.chemistry.api.registry;

import java.util.Map;
import java.util.HashMap;

/**
 * An implementation of {@link IChemistryRegistry} which simply uses a {@link HashMap} to store its {@link IRegisteredChemistryObject}s.
 * Once {@link HashMapRegistry#finalizeRegistration() finalized}, if any {@link IRegisteredChemistryObject} is attempted to be {@link IChemistryRegistry#register(IRegisteredChemistryObject) registered}, an error is thrown.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class HashMapRegistry<ID, T extends IRegisteredChemistryObject<T, ID>> implements IChemistryRegistry<T, ID> {

    private boolean done = false;

    private final Map<ID, T> map = new HashMap<>();

    /**
     * Add an object to this registry under the object's {@link IRegisteredChemistryObject#getId() id}.
     * Objects should cannot be registered during runtime, or else an exception will be raised.
     * @param object An object to store, which should be accessible at any point during runtime
     * @throws IllegalArgumentException If the {@link IRegisteredChemistryObject} has no {@link IRegisteredChemistryObject#getId() id}
     * @throws IllegalStateException If this registry can no longer register new {@link IRegisteredChemistryObject}s
     * @since Destroy 1.0
     * @author petrolpark
     */
    @Override
    public void register(T object) throws IllegalArgumentException, IllegalStateException {
        if (done) throw new IllegalStateException("Cannot register objects during runtime.");
        ID id = object.getId();
        if (id == null) throw new IllegalArgumentException("Cannot register an object with no ID: "+object.toString());
        map.put(id, object);
    };

    @Override
    public T get(ID id) {
        return map.get(id);
    };

    @Override
    public boolean isRegistered(ID id) {
        return map.containsKey(id);
    };

    @Override
    public boolean isRegistered(T object) {
        return map.containsValue(object);
    };

    @Override
    public void finalizeRegistry() {
        done = true;
    };
    
};
