package com.petrolpark.destroy.chemistry.api.registry.event;

import com.petrolpark.destroy.chemistry.api.event.IChemistryEvent;
import com.petrolpark.destroy.chemistry.api.registry.IChemistryRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IPriorityRegistration;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;

/**
 * Propose {@link IRegisteredChemistryObject}s to be added to a {@link IChemistryRegistry}.
 * <p><b>Usually fired: </b>Once per registry, during startup</p>
 * <p><b>Not cancellable</b></p>
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see AfterRegistrationEvent
 */
public class PriorityRegistrationEvent<T extends IRegisteredChemistryObject<T, ID>, ID, R extends IChemistryRegistry<T, ID>> implements IChemistryEvent {

    private final IPriorityRegistration<T, ID, R> priorityRegistration;
    
    public PriorityRegistrationEvent(final IPriorityRegistration<T, ID, R> priorityRegistration) {
        this.priorityRegistration = priorityRegistration;
    };

    /**
     * Propose a {@link IRegisteredChemistryObject} to put in a {@link IChemistryRegistry}.
     * This will not actually add the {@link IRegisteredChemistryObject} to the {@link IChemistryRegistry} yet - that is done later.
     * @param object The {@link IRegisteredChemistryObject} to put in the registry
     * @param priority If two {@link IRegisteredChemistryObject}s get registered under the same {@link IRegisteredChemistryObject#getId() id}, the one registered by this same {@link IPriorityRegistration} with the explictly greater {@code priority}, or else the first-registered {@link IRegisteredChemistryObject} will be the one actually added to the {@link IChemistryRegistry}.
     * @return {@code true} if the {@link IRegisteredChemistryObject} will be supplied to the {@link IChemistryRegistry} under that {@link IRegisteredChemistryObject#getId() id}, either because it was the first to claim it or because it usurped the existing {@link IRegisteredChemistryObject}
     * @see IRegisteredChemistryObject#isRegistered Ensuring (the) object is registered at runtime
     */
    public boolean register(T object, int priority) {
        return priorityRegistration.register(object, priority);
    };

    @Override
    public boolean isCancellable() {
        return false;
    };
    
};
