package ai_algorithm.specific_algorithm_logic.csp;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Constraint;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_examples.NotEqualConstraint;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.CombinationGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a constraint that requires all variables to have different values.
 *
 * @param <VAR> Type used for variables in the constraint.
 * @param <VAL> Type used for values in the constraint.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class AllDiffConstraint<VAR extends Variable, VAL> implements Constraint<VAR, VAL>, Cloneable {

    private List<VAR> variables; // the variables of the constraint

    /**
     * Constructs a new constraint with the given variables.
     *
     * @param variables The variables of the constraint.
     */
    public AllDiffConstraint(List<VAR> variables) {
        this.variables = variables;
    }

    /**
     * Returns the variables of the constraint.
     *
     * @return The variables of the constraint.
     */
    @Override
    public List<VAR> getScope() {
        return variables;
    }

    /**
     * Returns true if the constraint is satisfied with the given assignment.
     *
     * @param assignment The assignment to check.
     * @return True if the constraint is satisfied with the given assignment.
     */
    @Override
    public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
        HashSet<VAL> valueSet = new HashSet(); // the set of values
        for (VAR variable: variables) {
            if (assignment.contains(variable)) { // if the assignment contains the variable
                VAL value = assignment.getValue(variable); // get the value of the variable
                if (!valueSet.add(value)) { // if the value is already in the set
                    return false;   // return false
                }
            }
        }
        return true;
    }

    /**
     * @return A string representation of the constraint.
     */
    @Override
    public String toString() {
        StringBuilder sbl = new StringBuilder(); // create a new string Builder
        sbl.append("AD("); // append "AD(" to the string Builder
        for (Variable variable: variables.subList(0, variables.size() - 1)) {
            sbl.append(variable.toString()).append(", "); // append the variable and a comma to the string Builder
        }
        sbl.append(variables.get(variables.size() - 1)).append(")"); // append the last variable and a closing bracket to the string Builder
        return sbl.toString(); // return the string Builder as a string
    }

    /**
     * Converts this constraint to a list of NotEqualConstraints.
     *
     * @return A list of NotEqualConstraints.
     */
    public List<NotEqualConstraint> toNotEqualConstraint() {
        List<NotEqualConstraint> constraints = new ArrayList(); // create a new list of NotEqualConstraints
        CombinationGenerator.generateCombinations(variables, 2).forEach( // generate all combinations of the variables
                list -> constraints.add(new NotEqualConstraint(list.get(0), list.get(1))) // add a new NotEqualConstraint for each combination
        );
        return constraints;
    }

}