package com.petrolpark.destroy.chemistry.api.registry.event;

import com.petrolpark.destroy.chemistry.api.event.IChemistryEvent;
import com.petrolpark.destroy.chemistry.api.registry.IChemistryRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;

/**
 * Modify a registry after the normal method of registration (the {@link PriorityRegistrationEvent}) is complete.
 * This allows final additions and removals from a {@link IChemistryRegistry} before entering runtime, after which the registry cannot be modified.
 * <p><b>Usually fired: </b>Once per registry, during startup</p>
 * <p><b>Not cancellable</b></p>
 * @since 1.0
 * @author petrolpark
 * @see PriorityRegistrationEvent
 */
public class AfterRegistrationEvent<T extends IRegisteredChemistryObject<T, ID>, ID, R extends IChemistryRegistry<T, ID>> implements IChemistryEvent {

    private final R registry;

    public AfterRegistrationEvent(R registry) {
        this.registry = registry;
    };

    public R getRegistry() {
        return registry;
    };

    @Override
    public boolean isCancellable() {
        return false;
    };
    
};
