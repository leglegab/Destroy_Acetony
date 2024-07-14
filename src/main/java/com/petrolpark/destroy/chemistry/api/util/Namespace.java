package com.petrolpark.destroy.chemistry.api.util;

/**
 * A unique identifier for a platform or Destroy add-on which adds its own {@link com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject IRegisteredChemistryObjects}, subscribes to {@link com.petrolpark.destroy.chemistry.api.event.IChemistryEvent Events}, etc.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class Namespace {
    
    public final String value;

    public Namespace(String namespace) {
        value = namespace;
    };

    public static final Namespace of(String namespace) {
        return new Namespace(namespace);
    };

    @Override
    public String toString() {
        return value;
    };

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Namespace ns && ns.value.equals(value)) || (obj instanceof String string && string.equals(value));
    };
};
