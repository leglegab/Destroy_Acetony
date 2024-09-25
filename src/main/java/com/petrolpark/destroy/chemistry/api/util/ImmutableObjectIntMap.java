package com.petrolpark.destroy.chemistry.api.util;

import java.util.Map;

/**
 * Essentially a read-only {@link Map} with the value type being the primitive {@code int}.
 * Values which are not mapped to a value (and so would return {@code null} in a regular {@link Map}) will instead give {@code 0}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface ImmutableObjectIntMap<T> {
    
    /**
     * Returns the {@code int} associated with the given key.
     * @param key
     * @return {@code 0} if that key has no associated value.
     * @see Map#get(Object) Intended behaviour
     */
    public int get(T key);

    /**
     * Iterate over every entry in this Map.
     * @param consumer
     * @see Map#forEach(java.util.function.BiConsumer) Intended behaviour
     */
    public void forEach(IntObjectConsumer<T> consumer);
};
