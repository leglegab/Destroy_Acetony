package com.petrolpark.destroy.chemistry.api.event;

/**
 * An object which can fire {@link IChemistryEvent}s.
 * It is the role of implementations to actually get the {@link IChemistryEvent}s (wrapped in {@link IFiredChemistryEvent}s) to event bus subscribers.
 * Implementation of this interface will be platform-dependent.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IChemistryEventFirer {
    
    public <E extends IChemistryEvent> IFiredChemistryEvent<E> fire(final E chemistryEvent);
};
