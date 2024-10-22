package ai_algorithm.specific_algorithm_logic.csp.csp_examples;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Constraint;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Link to the original source from AIMA:
 * Link: https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/examples/NotEqualConstraint.java
 *
 * Represents a binary constraint which forbids equal values.
 *
 * The NotEqualConstraint class is a binary
 * constraint which forbids equal values.
 *
 * @param <VAR> Type of the variables.
 * @param <VAL> Type of the values.
 *
 * @return Type of the values.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class NotEqualConstraint<VAR extends Variable, VAL> implements Constraint<VAR, VAL> {

    // var1 and var2 are the two variables that are not allowed to have the same value.
    private VAR var1; // first variable
    private VAR var2; // second variable
    private List<VAR> scope; // scope is a list of variables which are constrained by this constraint.

    /**
     * Constructs a constraint with the given variables.
     *
     * @param var1 first variable
     * @param var2 second variable
     *
     * @throws IllegalArgumentException if both variables are the same.
     */
    public NotEqualConstraint(VAR var1, VAR var2) {
        this.var1 = var1;
        this.var2 = var2;
        scope = new ArrayList<>(2);
        scope.add(var1); // add var1 to the scope list.
        scope.add(var2); // add var2 to the scope list.
    }

    /**
     * Returns the scope of this constraint.
     *
     * @return a list of variables which are constrained by this constraint.
     */
    @Override
    public List<VAR> getScope() {
        return scope;
    }

    /**
     * Returns true if the given assignment is consistent with this constraint.
     *
     * @param assignment the assignment to be checked for consistency with this constraint.
     *
     * @return true if the given assignment is consistent with this constraint.
     */
    @Override
    public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
        VAL value1 = assignment.getValue(var1); // get the value of var1 from the assignment.
        return value1 == null || !value1.equals(assignment.getValue(var2));
    }

}