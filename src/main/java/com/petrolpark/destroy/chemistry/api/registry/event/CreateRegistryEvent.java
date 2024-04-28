package com.petrolpark.destroy.chemistry.api.registry.event;

import com.petrolpark.destroy.chemistry.api.event.IChemistryEvent;
import com.petrolpark.destroy.chemistry.api.registry.HashMapRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IChemistryRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;

/**
 * By default, Destroy uses a {@link HashMapRegistry} for each type of {@link IRegisteredChemistryObject}.
 * With this you can {@link CreateRegistryEvent#setRegistry(IChemistryRegistry) replace} the registry used.
 * You may want to do this to use a faster or more efficient way of registering things.
 * <p><b>Usually fired: </b>Once per registry, during startup, before anything is registered to the given registry</p>
 * <p><b>If cancelled: </b>No additional effect</p>
 * @see PriorityRegistrationEvent Adding {@link IRegisteredChemistryObject}s to the {@link IChemistryRegistry}
 * @since 1.0
 * @author petrolpark
 */
public class CreateRegistryEvent<T extends IRegisteredChemistryObject<T, ID>, ID> implements IChemistryEvent {

    protected IChemistryRegistry<T, ID> registry;

    /**
     * The current {@link IChemistryRegistry} for this {@link IRegisteredChemistryObject}.
     * By default this will be a {@link HashMapRegistry}.
     * @return A {@link IChemistryRegistry} - don't register anything to this, now!
     */
    public IChemistryRegistry<T, ID> getRegistry() {
        return registry;
    };

    /**
     * Replace the existing registry with a different one.
     * @param registry
     */
    public void setRegistry(IChemistryRegistry<T, ID> registry) {
        this.registry = registry;
    };

    @Override
    public boolean isCancellable() {
        return true;
    };
    
};
