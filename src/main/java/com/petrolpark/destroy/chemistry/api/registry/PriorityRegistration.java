package com.petrolpark.destroy.chemistry.api.registry;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.petrolpark.destroy.chemistry.api.util.AttachedInt;

/**
 * Default implementation of {@link IPriorityRegistration}.
 * This stores all {@code id}s, {@link IRegisteredChemistryObject}s and {@code priority}s in a weakly-referenced {@link Map}, which are then all loaded into the {@link IChemistryRegistry}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public class PriorityRegistration<T extends IRegisteredChemistryObject<T, ID>, ID, R extends IChemistryRegistry<T, ID>> implements IPriorityRegistration<T, ID, R> {

    private final IChemistryRegistry<T, ID> registry;

    /**
     * A set of {@code id}s indexing {@link IRegisteredChemistryObject}s along with the {@code priority} currently associated with that {@code id}, which is used to check if {@link IRegisteredChemistryObject}s should be usurped.
     * It is a {@link WeakReference} to encourage quick discarding after it's finished with.
     */
    private final WeakReference<Map<ID, AttachedInt<T>>> objectsToRegister;

    /**
     * Create a {@link IPriorityRegistration} which can be fired in a {@link }
     * @param registry The {@link IChemistryRegistry} into which the values this {@link IPriorityRegistration} collects will eventually be put
     */
    public PriorityRegistration(R registry) {
        this.registry = registry;
        objectsToRegister = new WeakReference<Map<ID,AttachedInt<T>>>(new HashMap<>());
    };
    
    @Override
    public boolean register(final T object, final int priority) {
        return object == objectsToRegister.get().compute(object.getId(), (id, oldObject) -> priority > oldObject.value ? AttachedInt.of(object, priority) : oldObject).object;
    };

    @Override
    public void finalizeRegistration() {
        objectsToRegister.get().forEach((id, object) -> registry.register(object.object));
    };

    
};
