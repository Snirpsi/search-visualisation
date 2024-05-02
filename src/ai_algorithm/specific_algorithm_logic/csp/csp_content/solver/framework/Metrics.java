package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.framework;

import java.util.Hashtable;
import java.util.Set;
import java.util.TreeMap;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/framework/Metrics.java
 *
 * Stores key-value pairs for efficiency analysis.
 *
 * @author Ravi Mohan
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class Metrics {
    private Hashtable<String, String> hash; // Stores key-value pairs.

    /** Initializes an empty metrics object. */
    public Metrics() {
        this.hash = new Hashtable<String, String>();
    }

    /** Sets the value of a key to an integer. */
    public void set(String name, int i) {
        hash.put(name, Integer.toString(i)); // Puts the key-value pair into the hash table.
    }

    /** Sets the value of a key to a double. */
    public void set(String name, double d) {
        hash.put(name, Double.toString(d)); // Puts the key-value pair into the hash table.
    }

    /** Increments the value of a key by 1. */
    public void incrementInt(String name) {
        set(name, getInt(name) + 1); // Increments the value of the key by 1.
    }

    /** Sets the value of a key to a long. */
    public void set(String name, long l) {
        hash.put(name, Long.toString(l)); // Puts the key-value pair into the hash table.
    }

    /** Returns the integer value of a key. */
    public int getInt(String name) {
        String value = hash.get(name); // Gets the value of the key.
        return value != null ? Integer.parseInt(value) : 0; // Returns the integer value of the key.
    }

    /** Returns the double value of a key. */
    public double getDouble(String name) {
        String value = hash.get(name); // Gets the value of the key.
        return value != null ? Double.parseDouble(value) : Double.NaN; // Returns the double value of the key.
    }

    /** Returns the long value of a key. */
    public long getLong(String name) {
        String value = hash.get(name); // Gets the value of the key.
        return value != null ? Long.parseLong(value) : 0l;
    }

    /** Returns the name of a key as string. */
    public String get(String name) {
        return hash.get(name); // Returns the value of the key.
    }

    /** Returns the set of keys. */
    public Set<String> keySet() {
        return hash.keySet(); // Returns the set of keys.
    }

    /** Sorts the key-value pairs by key names and formats them as equations. */
    public String toString() {
        TreeMap<String, String> map = new TreeMap<String, String>(hash); // Sorts the key-value pairs by key names.
        return map.toString();
    }
}