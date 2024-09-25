package com.petrolpark.destroy.chemistry.api.species;

import com.petrolpark.destroy.chemistry.api.property.INamespace;
import com.petrolpark.destroy.chemistry.api.registry.IRegisteredChemistryObject;
import com.petrolpark.destroy.chemistry.api.util.Namespace;

/**
 * An ID which uniquely identifies a {@link IRegisteredChemistryObject}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public class NamespacedId implements INamespace {

    protected final Namespace namespace;
    protected final String value;

    public NamespacedId(Namespace namespace, String value) {
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
     * Whether the supplied {@link Object} is a {@link NamespacedId} with equal values to this one, or a {@link String} to which this {@link NamespacedId} would serialize.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NamespacedId sid && sid.namespace.equals(namespace) && sid.value.equals(value)) || (obj instanceof String string && toString().equals(string));
    };
    
};
