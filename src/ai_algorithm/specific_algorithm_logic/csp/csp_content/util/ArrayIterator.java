package ai_algorithm.specific_algorithm_logic.csp.csp_content.util;

import java.util.Iterator;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/util/ArrayIterator.java
 *
 * Iterates efficiently through an array.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class ArrayIterator<T> implements Iterator<T> {

    T[] values; // VAL[] values;
    int counter; // The counter for the iterator.

    /**
     * Constructor.
     * Creates a new ArrayIterator.
     *
     * @param values The values to iterate through.
     */
    public ArrayIterator(T[] values) {
        this.values = values;
        counter = 0;
    }

    /**
     * @return True if there are more elements to iterate through, false otherwise.
     */
    public boolean hasNext() {
        return counter < values.length;
    }

    /**
     * @return The next element in the iteration.
     */
    public T next() {
        return values[counter++];
    }

    /**
     * Removes the current element from the iteration.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}