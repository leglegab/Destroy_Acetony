package com.petrolpark.destroy.chemistry.api.util;

/**
 * A {@link Couple} where the order of the objects is preserved.
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see Couple Couples where the order is irrelevant
 */
public class OrderedCouple<O> extends Couple<O> {

    /**
     * Construct an {@link OrderedCouple}. The order of these two objects is preserved.
     * @param first
     * @param second
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public OrderedCouple(O first, O second) {
        super(first, second);
    };

    /**
     * @param first
     * @return One of the objects in this {@link Couple}, which may be {@code null}
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see OrderedCouple#getFirst()
     * @see OrderedCouple#getSecond()
     */
    public O get(boolean first) {
        if (first) return object1;
        return object2;
    };

    /**
     * @return The first object in this {@link Couple}, which may be {@code null}
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see OrderedCouple#get(boolean)
     * @see OrderedCouple#getSecond()
     */
    public O getFirst() {
        return get(true);
    };

    /**
     * @return The second object in this {@link Couple}, which may be {@code null}
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see OrderedCouple#get(boolean)
     * @see OrderedCouple#getFirst()
     */
    public O getSecond() {
        return get(false);
    };

    /**
     * Get a new {@link OrderedCouple} instance whose {@link OrderedCouple#getFirst() first} object is this one's {@link OrderedCouple#getSecond() second}, and vice versa.
     * @return A new inverted {@link OrderedCouple} instance
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    public OrderedCouple<O> getOpposite() {
        return new OrderedCouple<O>(object2, object1);
    };

    /**
     * {@inheritDocs}
     * If the supplied object is an {@link OrderedCouple}, then the order of that {@link OrderedCouple}'s objects must be the same as this one.
     * If the supplied object is an (unordered) {@link Couple}, then the order in that {@link Couple} does not matter.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderedCouple<?> couple) {
            return couple.getFirst().equals(getFirst()) && couple.getSecond().equals(getSecond());
        };
        return super.equals(obj);
    };
    
};
