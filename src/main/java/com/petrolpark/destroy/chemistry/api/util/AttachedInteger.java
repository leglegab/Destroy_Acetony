package com.petrolpark.destroy.chemistry.api.util;

public class AttachedInteger<T> {
    
    public final T object;
    public final int value;

    private AttachedInteger(T object, int value) {
        this.object = object;
        this.value = value;
    };

    public static <U> AttachedInteger<U> of(U object) {
        return of(object, 0);
    };

    public static <U> AttachedInteger<U> of(U object, int value) {
        return new AttachedInteger<>(object, value);
    };

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AttachedInteger ai && ai.value == value && ai.object.equals(object));
    };
};
