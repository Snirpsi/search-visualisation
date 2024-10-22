package ai_algorithm.specific_algorithm_logic.csp.csp_content;

import java.util.*;

/**
 * An assignment assigns values to some or all variables of a CSP.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class Assignment<VAR extends Variable, VAL> implements Cloneable {
    /**
     * Maps variables to their assigned values.
     */
    private LinkedHashMap<VAR, VAL> variableToValueMap = new LinkedHashMap<>();

    /**
     * Creates an empty assignment.
     */
    public List<VAR> getVariables() {
        return new ArrayList<>(variableToValueMap.keySet()); // get the variables of the assignment
    }

    /**
     * Returns the value assigned to the specified variable.
     */
    public VAL getValue(VAR var) {
        return variableToValueMap.get(var); // get the value of the variable
    }

    /**
     * Adds a variable-value pair to this assignment.
     */
    public VAL add(VAR var, VAL value) {
        assert value != null; // null values are not allowed
        return variableToValueMap.put(var, value); // put the variable and value in the map
    }

    /**
     * Removes a variable from this assignment.
     */
    public VAL remove(VAR var) {
        return variableToValueMap.remove(var); // remove the variable from the map
    }

    /**
     * Returns true if this assignment contains a value for the specified variable.
     */
    public boolean contains(VAR var) {
        return variableToValueMap.containsKey(var); // return true if the map contains the variable
    }

    /**
     * Returns true if this assignment does not violate any constraints of the CSP.
     */
    public boolean isConsistent(List<Constraint<VAR, VAL>> constraints) {
        return constraints.stream().allMatch(cons -> cons.isSatisfiedWith(this)); // return true if all constraints are satisfied
    }

    /**
     * Returns true if this assignment assigns values to every variable of the CSP.
     * <code>vars</code>.
     */
    public boolean isComplete(List<VAR> vars) {
        return vars.stream().allMatch(this::contains);
    }

    /**
     * Returns true if this assignment is consistent as well as complete with respect to the given CSP.
     */
    public boolean isSolution(CSP<VAR, VAL> csp) {
        return isConsistent(csp.getConstraints()) && isComplete(csp.getVariables()); // return true if the assignment is consistent and complete
    }

    /**
     * Returns a deep copy of this object.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Assignment<VAR, VAL> clone() {
        Assignment<VAR, VAL> result;
        try {
            result = (Assignment<VAR, VAL>) super.clone();
            result.variableToValueMap = new LinkedHashMap<>(variableToValueMap);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException("Could not clone assignment."); // should never happen!
        }
        return result;
    }

    /**
     * Returns a string representation of this assignment.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<VAR, VAL> entry : variableToValueMap.entrySet()) {
            if (result.length() > 1)
                result.append(", ");
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        result.append("}");
        return result.toString();
    }
}