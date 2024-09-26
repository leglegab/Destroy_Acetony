package com.petrolpark.destroy.chemistry.api.util;

/**
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public class AttachedInt<T> {
    
    public final T object;
    public int value;

    private AttachedInt(T object, int value) {
        this.object = object;
        this.value = value;
    };

    public static <U> AttachedInt<U> of(U object) {
        return of(object, 0);
    };

    public static <U> AttachedInt<U> of(U object, int value) {
        return new AttachedInt<>(object, value);
    };

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AttachedInt ai && ai.value == value && ai.object.equals(object));
    };
};
