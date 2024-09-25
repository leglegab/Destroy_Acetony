package com.petrolpark.destroy.chemistry.api.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Two objects in no particular order. They may be {@code null}.
 * @since Destroy 0.1.0
 * @author petrolpark
 * @see OrderedCouple Couples which retain the order of the two objects
 */
public class Couple<O> implements Collection<O> {

    protected final O object1;
    protected final O object2;

    /**
     * Constructs a {@link Couple} of the given objects. Although they are labelled '1' and '2' they are not guaranteed to retain this ordering.
     * @param object1
     * @param object2
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see OrderedCouple Couples which retain the order of the two objects
     */
    public Couple(O object1, O object2) {
        this.object1 = object1;
        this.object2 = object2;
    };

    /**
     * Get the object in this {@link Couple} which is not the given one.
     * @param object
     * @return The other object in this {@link Couple}, which may be {@code null}
     * @throws IllegalArgumentException If the supplied object is not either of the objects in this {@link Couple}
     * @throws UnsupportedOperationException If both objects in this {@link Couple} are the same, meaning there is no 'other'
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see Couple#getNotEqual(Object) Checking by equality rather than reference
     */
    public O getNot(O object) {
        if (object1 == object2) throw new UnsupportedOperationException("Symmetrical Couple");
        if (object == object1) return object2;
        if (object == object2) return object1;
        throw new IllegalArgumentException(String.format("% is not one of the objects of this Couple", object));
    };

    /**
     * Get the object in this {@link Couple} which does not {@link Object#equals(Object) equal} the given one.
     * @param object
     * @return The other object in this {@link Couple}, which may be {@code null}
     * @throws IllegalArgumentException If the supplied object is not {@link Object#equals(Object) equal} to either of the objects in this {@link Couple}
     * @throws UnsupportedOperationException If both objects in this {@link Couple} are {@link Object#equals(Object) equal}, meaning there is no 'other'
     * @since Destroy 0.1.0
     * @author petrolpark
     * @see Couple#getNot(Object) Checking by reference rather than equality
     */
    public O getNotEqual(O object) {
        if (object1.equals(object2)) throw new UnsupportedOperationException("Symmetrically equal Couple");
        if (object.equals(object1)) return object2;
        if (object.equals(object2)) return object1;
        throw new IllegalArgumentException(String.format("% is not equal to one of the objects of this Couple", object));
    };

    /**
     * Whether the two objects contained by this {@link Couple} are {@link Object#equals(Object) equal} to those of the supplied {@link Couple}, in either order.
     * 
     * <pre>
     * &nbsp;Couple&lt;Object> coupleAB = new Couple&lt&gt(a1, b1);
     * Couple&lt;Object> coupleBA = new Couple&lt&gt(b2, a2);
     * Couple&lt;Object> coupleAA = new Couple&lt&gt(a3, a4);</pre>
     * <p>
     * with
     * <pre>
     * &nbsp;a1.equals(a2)
     * a1.equals(a3)
     * a1.equals(a4)
     * b1.equals(b4)</pre>
     * all being {@code true}, then
     * <pre>
     * &nbsp;coupleAB.equals(coupleBA)
     * </pre>
     * evaluates to {@code true}, while
     * <pre>
     * &nbsp;coupleAB.equals(coupleAA)
     * coupleBA.equals(coupleAA)</pre>
     * both evaluate to {@code false}.
     * @param obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Couple<?> couple)) return false;
        return (couple.object1.equals(object1) && couple.object2.equals(object2)) || (couple.object1.equals(object2) && couple.object2.equals(object1));
    };

    /**
     * Creates a couple with the same two objects.
     * @since Destroy 0.1.0
     * @author petrolpark
     */
    @Override
    public Couple<O> clone() throws CloneNotSupportedException {
        return new Couple<O>(object1, object2); 
    };

    /**
     * Pick a random one of the two objects in this {@link Couple}
     * @param random
     * @return One of the objects in this {@link Couple}, which may be {@code null}
     */
    public O getRandom(Random random) {
        return random.nextBoolean() ? object1 : object2;
    };

    @Override
    public final int size() {
        return 2;
    };

    @Override
    public boolean isEmpty() {
        return object1 == null && object2 == null;
    };

    @Override
    public boolean contains(Object o) {
        return o.equals(object1) || o.equals(object2);
    };

    @Override
    public Iterator<O> iterator() {
        return new CoupleIterator();
    };

    @Override
    public Object[] toArray() {
        return new Object[]{object1, object2};
    };

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < 2) return (T[]) Arrays.copyOf(new Object[]{object1, object2}, 2, a.getClass());
        a[0] = (T)object1;
        a[1] = (T)object2;
        if (a.length > 2) a[2] = null;
        return a;
    };

    protected class CoupleIterator implements Iterator<O> {

        protected boolean doneFirst = false;
        protected boolean doneSecond = false;

        @Override
        public boolean hasNext() {
            return !doneFirst || !doneSecond;
        };

        @Override
        public O next() {
            if (!doneFirst) {
                doneFirst = true;
                return object1;
            } else if (!doneSecond) {
                doneSecond = true;
                return object2;
            } else {
                throw new NoSuchElementException();
            }
        };

    };

    @Override
    @Deprecated
    public boolean add(O e) {
        throw new UnsupportedOperationException("Cannot add to Couples after creation.");
    };

    @Override
    @Deprecated
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot remove from Couples after creation.");
    };

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        };
        return true;
    };

    @Override
    @Deprecated
    public boolean addAll(Collection<? extends O> c) {
        throw new UnsupportedOperationException("Cannot add to Couples after creation.");
    };

    @Override
    @Deprecated
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot remove from Couples after creation.");
    };

    @Override
    @Deprecated
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot remove from Couples after creation.");
    };

    @Override
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException("Cannot remove from Couples after creation.");
    };

};
