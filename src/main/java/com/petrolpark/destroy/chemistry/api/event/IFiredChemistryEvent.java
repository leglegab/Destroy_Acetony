package com.petrolpark.destroy.chemistry.api.event;

/**
 * A wrapper for {@link IChemistryEvent}s, which are platform-independent.
 * Implementations of this interface will be platform-dependent. The correct implementation(s) will be selected by {@link Firer}.
 * A {@link IChemistryEventFirer} does not strictly need to utilize this class.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IFiredChemistryEvent<T extends IChemistryEvent> {
    
    /**
     * Get the {@link IChemistryEvent} wrapped by this object.
     * @return An {@link IChemistryEvent}
     */
    public T get();

    /**
     * Whether any of the recipients of this {@link IChemistryEvent} cancelled it.
     * @return {@code true} if any event subscriber somehow cancelled this event, regardless of whether the {@link IChemistryEvent} is {@link IChemistryEvent#isCancellable() is cancellable}.
     */
    public boolean isCancelled();
};
