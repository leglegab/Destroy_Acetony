package com.petrolpark.destroy.chemistry.api.event;

/**
 * An object which can be sent out at any time to an unknown number of event subscribers, and modified by them.
 * Some can also be {@link IChemistryEvent#isCancellable() cancelled}, which means they do not get sent to any more event subscribers.
 * You can check if an event got cancelled with {@link IFiredChemistryEvent#isCancelled()}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IChemistryEvent {

    /**
     * Whether cancelling this {@link IChemistryEvent} has any effect.
     * The result of this does not enforce that any {@link IChemistryEventFirer}s prevent {@link IFiredChemistryEvent}s from being cancelled (they still may well be) - technically it only informs whether the result of cancelling is used by whichever routine {@link Firer#fire(IChemistryEvent) fired} the event.
     * However, {@link IChemistryEventFirer}s <em>should</em> use this to judge whether they should stop sending an event to subscribers before it's sent to all of them.
     */
    public boolean isCancellable();
};
