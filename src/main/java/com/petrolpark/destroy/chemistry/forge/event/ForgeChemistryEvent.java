package com.petrolpark.destroy.chemistry.forge.event;

import com.petrolpark.destroy.chemistry.api.event.IChemistryEvent;
import com.petrolpark.destroy.chemistry.api.event.IFiredChemistryEvent;

import net.minecraftforge.eventbus.api.Event;

/**
 * The Minecraft Forge implementation of {@link IFiredChemistryEvent}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public class ForgeChemistryEvent<E extends IChemistryEvent> extends Event implements IFiredChemistryEvent<E> {

    protected final E wrapped;

    public ForgeChemistryEvent(E chemistryEvent) {
        wrapped = chemistryEvent;
    };

    @Override
    public E get() {
        return wrapped;
    };

    /**
     * If this is {@code true}, invoking {@link Event#setCanceled(boolean)} will:
     * <ul>
     * <li>Stop this event from being sent to any more event bus subscribers</li>
     * <li>Possibly have an effect on whichever routine called the {@link IChemistryEvent}
     * </ul>
     */
    @Override
    public boolean isCancelable() {
        return wrapped.isCancellable();
    };

    @Override
    public boolean isCancelled() {
        return isCanceled();
    };

};
