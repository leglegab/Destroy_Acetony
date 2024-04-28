package com.petrolpark.destroy.chemistry.api.registry;

/**
 * An object whose intended use is to collect {@link IRegisteredChemistryObject} to store in a {@link IChemistryRegistry}.
 * It allows multiple {@link com.petrolpark.destroy.chemistry.api.event.IChemistryEvent IChemistryEvent} subscribers to attempt to register objects with the same {@code ID}, by picking the one registered with the highest priority.
 * @since 1.0
 * @author petrolpark
 * @see PriorityRegistration Default implementation
 */
public interface IPriorityRegistration<T extends IRegisteredChemistryObject<T, ID>, ID, R extends IChemistryRegistry<T, ID>> {

    /**
     * Propose a {@link IRegisteredChemistryObject} to put in a {@link IChemistryRegistry}.
     * This will not actually add the {@link IRegisteredChemistryObject} to the {@link IChemistryRegistry} yet - that is done later.
     * The {@code id} is not checked against those already in the {@link IChemistryRegistry}, hence why {@link IPriorityRegistration}s should only be used once per registry, as nothing more than a convenience tool.
     * @param object The {@link IRegisteredChemistryObject} to put in the registry
     * @param priority If two {@link IRegisteredChemistryObject}s get registered under the same {@link IRegisteredChemistryObject#getId() id}, the one registered by this same {@link IPriorityRegistration} with the explictly greater {@code priority}, or else the first-registered {@link IRegisteredChemistryObject} will be the one actually added to the {@link IChemistryRegistry}.
     * @return {@code true} if the {@link IRegisteredChemistryObject} will be supplied to the {@link IChemistryRegistry} under that {@link IRegisteredChemistryObject#getId() id}, either because it was the first to claim it or because it usurped the existing {@link IRegisteredChemistryObject}.
     * @see IRegisteredChemistryObject#isRegistered Ensuring (the) object is registered at runtime
     */
    public boolean register(T object, int priority);

    /**
     * Put all {@link IRegisteredChemistryObject}s due for registration in the {@link IChemistryRegistry}.
     */
    public void finalizeRegistration();
    
};
