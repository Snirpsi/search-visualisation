package ai_algorithm.specific_algorithm_logic.csp.csp_content;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.ArrayIterator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/Domain.java
 *
 * A domain Di consists of a set of allowable values {v1, ... , vk} for the
 * corresponding variable Xi and defines a default order on those values. This
 * implementation guarantees, that domains are never changed after they have
 * been created. Domain reduction is implemented by replacement instead of
 * modification. So previous states can easily and safely be restored.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 *
 */
public class Domain<VAL> implements Iterable<VAL> {

    private final Object[] values; // VAL[] values;

    /**
     * Constructor.
     * Creates a new domain.
     * @param values The List of values of the domain.
     */
    public Domain(List<VAL> values) {
        this.values = values.toArray();
    }

    /**
     * Constructor.
     * Creates a new domain.
     * @param values The values of the domain.
     */
    @SafeVarargs
    public Domain(VAL... values) {
        this.values = values;
    }

    /**
     * Returns the size of the domain.
     * @return The size of the domain.
     */
    public int size() {
        return values.length;
    }

    /**
     * Returns the value at the specified index.
     * @param index The index of the value.
     * @return The value at the specified index.
     */
    @SuppressWarnings("unchecked")
    public VAL get(int index) {
        return (VAL) values[index];
    }

    /**
     * Checks if the domain is empty.
     * @return True if the domain is empty, false otherwise.
     */
    public boolean isEmpty() {
        return values.length == 0;
    }

    /**
     * Checks if the domain contains the specified value.
     * @param value The value to check.
     * @return True if the domain contains the value, false otherwise.
     */
    public boolean contains(VAL value) {
        for (Object v : values)
            if (value.equals(v))
                return true;
        return false;
    }

    /**
     * @return An iterator over the values in the domain.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<VAL> iterator() {
        return new ArrayIterator<>((VAL[]) values);
    }

    /** Not very efficient... */
    @SuppressWarnings("unchecked")
    public List<VAL> asList() {
        return Arrays.asList((VAL[]) values);
    }

    /**
     * Checks if the object is equal to the current domain.
     * Domains with equal values are equal.
     * @param obj The object to compare.
     * @return True if the object is equal to the current domain, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && Arrays.equals(values, ((Domain) obj).values);
    }

    /**
     * @return The hash code of the domain.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    /**
     * @return The string representation of the domain.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (Object value : values) {
            if (result.length() > 1)
                result.append(", ");
            result.append(value);
        }
        result.append("}");
        return result.toString();
    }
}