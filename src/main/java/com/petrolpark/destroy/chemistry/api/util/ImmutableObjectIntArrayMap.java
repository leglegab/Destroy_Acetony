package com.petrolpark.destroy.chemistry.api.util;

import java.util.Arrays;

/**
 * An implementation of {@link ImmutableObjectIntMap} which just iterates over a set of keys to find the matching one.
 * Suitable for smaller {@link ImmutableObjectIntMap}s.
 * @since Destroy 1.0
 * @author petrolpark
 */
public class ImmutableObjectIntArrayMap<T> implements ImmutableObjectIntMap<T> {

    protected final T[] keys;
    protected final int[] values;

    /**
     * Construct an {@link ImmutableObjectIntArrayMap} where each key in the key array is mapped to the {@code int} with the same index in the value array.
     * @param keys
     * @param values
     * @throws IllegalArgumentException If the numbers of keys and values do not match.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public ImmutableObjectIntArrayMap(T[] keys, int[] values) {
        if (keys.length != values.length) throw new IllegalArgumentException(String.format("Number of keys (%) does not match number of values (%)", keys.length, values.length));
        this.keys = Arrays.copyOf(keys, keys.length);
        this.values = Arrays.copyOf(values, keys.length);
    };

    @Override
    public int get(T key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key) return values[i];
        };
        return 0;
    };

    @Override
    public void forEach(IntObjectConsumer<T> consumer) {
        for (int i = 0; i < keys.length; i++) {
            consumer.consume(values[i], keys[i]);
        };
    };
    
};
