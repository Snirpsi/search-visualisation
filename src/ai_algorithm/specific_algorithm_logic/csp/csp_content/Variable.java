package ai_algorithm.specific_algorithm_logic.csp.csp_content;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/Variable.java
 *
 * A variable is a distinguishable object with a name.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class Variable {
    private final String name; // The name of the variable.

    /**
     * Constructor for the Variable class.
     * @param name The name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Getter for the name of the variable.
     * @return The name of the variable.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the name of the variable.
     * @return The name of the variable.
     */
    public String toString() {
        return name;
    }

    /**
     * Checks if the object is equal to the current variable.
     * Variables with equal names are equal.
     * @param obj The object to compare.
     * @return True if the object is equal to the current variable, false otherwise.
     */
    @Override
    public final boolean equals(Object obj) {
        return (obj != null && obj.getClass() == getClass()) && name.equals(((Variable) obj).name);
    }

    /**
     * Returns the hash code of the variable.
     * @return The hash code of the variable.
     */
    @Override
    public final int hashCode() {
        return name.hashCode();
    }
}