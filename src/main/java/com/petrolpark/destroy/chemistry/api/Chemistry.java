package com.petrolpark.destroy.chemistry.api;

import java.util.function.Consumer;

import com.petrolpark.destroy.chemistry.api.event.Firer;
import com.petrolpark.destroy.chemistry.api.event.IChemistryEventFirer;
import com.petrolpark.destroy.chemistry.api.nuclide.ElementAveragedNuclide;
import com.petrolpark.destroy.chemistry.api.registry.DummyRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IChemistryRegistry;
import com.petrolpark.destroy.chemistry.api.registry.IPriorityRegistration;
import com.petrolpark.destroy.chemistry.api.registry.PriorityRegistration;
import com.petrolpark.destroy.chemistry.api.registry.event.AfterRegistrationEvent;
import com.petrolpark.destroy.chemistry.api.registry.event.CreateRegistryEvent;
import com.petrolpark.destroy.chemistry.api.registry.event.PriorityRegistrationEvent;
import com.petrolpark.destroy.chemistry.api.species.ISpecies;
import com.petrolpark.destroy.chemistry.api.species.IRegisteredSpecies;
import com.petrolpark.destroy.chemistry.api.species.ISpeciesComparator;

/**
 * The main class for Destroy's chemistry system.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class Chemistry {

    /**
     * The registry of {@link ElementAveragedNuclide "elements"} known to Destroy. It is possible that add-ons replace or ignore this.
     */
    public static IChemistryRegistry<ElementAveragedNuclide, String> ELEMENTS = new DummyRegistry<>();

    /**
     * The default {@link ISpeciesComparator} primarily used to check novel {@link ISpecies} against {@link IRegisteredSpecies known Species}.
     */
    public static ISpeciesComparator SPECIES_COMPARATOR;
    
    /**
     * Go through the default initiation for Destroy's chemistry system.
     * @param eventFirer The {@link IChemistryEventFirer} to use for this platform, which must be ready to fire events straight away
     */
    public static final void initiate(final IChemistryEventFirer eventFirer, final Consumer<String> logger) {

        // Tell the system how to fire events
        Firer.register(eventFirer);

        // Register "elements"
        logger.accept("Registering elements...");
        ELEMENTS = Firer.fire(new CreateRegistryEvent<ElementAveragedNuclide, String>()).get().getRegistry();
        final IPriorityRegistration<ElementAveragedNuclide, String, IChemistryRegistry<ElementAveragedNuclide, String>> elementRegistration = new PriorityRegistration<>(ELEMENTS);
        Firer.fire(new PriorityRegistrationEvent<>(elementRegistration));
        elementRegistration.finalizeRegistration();
        Firer.fire(new AfterRegistrationEvent<>(ELEMENTS));
        ELEMENTS.finalizeRegistry();
    };
};
