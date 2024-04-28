package com.petrolpark.destroy.chemistry.api.event;

/**
 * The master class for firing {@link IChemistryEvent}s.
 * Chemistry Events can be fired on any platform simply by invoking {@link Firer#fire(IChemistryEvent)}.
 * On a platform, {@link Firer#register(IChemistryEventFirer)} must be called exactly once, before any Events are fired.
 * @since 1.0
 * @author petrolpark
 */
public final class Firer {
    
    /**
     * The registered {@link IChemistryEventFirer}s.
     */
    private static IChemistryEventFirer FIRER = null;

    /**
     * Fire a {@link IChemistryEvent}, regardless of what platform we're on.
     * The {@link IChemistryEvent} is sent to all {@link Firer#FIRERS Firers} in sequence, without any guarantee of order.
     * @param <E> Type of the Event
     * @param event A new Event instance
     * @return The modified Event
     * @throws IllegalStateException if this is being called before any {@link IChemistryEventFirer} has been registered
     */
    public static final <E extends IChemistryEvent> IFiredChemistryEvent<E> fire(E event) {
        if (FIRER == null) throw new IllegalStateException("The Chemistry Event Firer has not yet been registered.");
        return FIRER.fire(event);
    };

    /**
     * Add a {@link IChemistryEventFirer Firer} for this platform.
     * No guarantee is made about the order in which Firers will fire a {@link IChemistryEvent}.
     * @param firer
     * @throws UnsupportedOperationException if the Firer is being registered too late
     */
    public static final void register(final IChemistryEventFirer firer) {
        if (FIRER != null) throw new IllegalStateException("Can only register a single Chemistry Event Firer.");
    };
};
