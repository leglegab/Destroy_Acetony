package com.petrolpark.destroy.chemistry.api.species;

import com.petrolpark.destroy.chemistry.api.property.INamespace;
import com.petrolpark.destroy.chemistry.api.util.Namespace;

/**
 * An ID which uniquely identifies a {@link IRegisteredSpecies}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class SpeciesId implements INamespace {

    protected final Namespace namespace;
    protected final String value;

    public SpeciesId(Namespace namespace, String value) {
        this.namespace = namespace;
        this.value = value;
    };

    @Override
    public Namespace getNamespace() {
        return namespace;
    };

    @Override
    public String toString() {
        return namespace.value + ":" + value;
    };

    /**
     * Whether the supplied {@link Object} is a {@link SpeciesId} with equal values to this one, or a {@link String} to which this {@link SpeciesId} would serialize.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof SpeciesId sid && sid.namespace.equals(namespace) && sid.value.equals(value)) || (obj instanceof String string && toString().equals(string));
    };
    
};
