package com.petrolpark.destroy.chemistry.api.util;

import java.util.function.BiConsumer;

/**
 * Essentially a {@link BiConsumer} with the first type being the primitive {@code int}.
 * @since Destroy 0.1.0
 * @author petrolpark
 */
@FunctionalInterface
public interface IntObjectConsumer<T> {
    
    public void consume(int i, T object);
};
