package com.petrolpark.destroy.chemistry.forge.event;

import com.petrolpark.destroy.chemistry.api.event.IChemistryEvent;
import com.petrolpark.destroy.chemistry.api.event.IChemistryEventFirer;

import net.minecraftforge.common.MinecraftForge;

/**
 * The Minecraft Forge implementation of {@link IChemistryEventFirer}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class ForgeChemistryEventFirer implements IChemistryEventFirer {

    @Override
    public <E extends IChemistryEvent> ForgeChemistryEvent<E> fire(E chemistryEvent) {
        ForgeChemistryEvent<E> forgeEvent = new ForgeChemistryEvent<E>(chemistryEvent);
        MinecraftForge.EVENT_BUS.post(forgeEvent);
        return forgeEvent;
    };


    
};
