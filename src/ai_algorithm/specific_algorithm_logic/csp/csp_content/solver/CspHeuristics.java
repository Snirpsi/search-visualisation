package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Constraint;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/CspHeuristics.java
 *
 * Defines variable and value selection heuristics for CSP backtracking strategies.
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class CspHeuristics {

    /**
     * Interface for variable selection strategies.
     * @param <VAR> Type which is used to represent variables
     * @param <VAL> Type which is used to represent the values in the domains
     * @return List of variables
     */
    public interface VariableSelectionStrategy<VAR extends Variable, VAL> {
        // Returns the variables from vars which are the best with respect to some heuristic.
        List<VAR> apply(CSP<VAR, VAL> csp, List<VAR> vars);
    }

    /**
     * Interface for value ordering strategies.
     * @param <VAR> Type which is used to represent variables
     * @param <VAL> Type which is used to represent the values in the domains
     * @return List of values
     */
    public interface ValueOrderingStrategy<VAR extends Variable, VAL> {
        // Returns the values of Dom(var) in a special order.
        List<VAL> apply(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);
    }

    // The methods are used to create instances of the strategies in a convenient way.
    public static <VAR extends Variable, VAL> VariableSelectionStrategy<VAR, VAL> mrv() { return new MrvHeuristic<>(); }
    public static <VAR extends Variable, VAL> VariableSelectionStrategy<VAR, VAL> deg() { return new DegHeuristic<>(); }
    public static <VAR extends Variable, VAL> VariableSelectionStrategy<VAR, VAL> mrvDeg() {
        return (csp, vars) -> new DegHeuristic<VAR, VAL>().apply(csp, new MrvHeuristic<VAR, VAL>().apply(csp, vars));
    }
    public static <VAR extends Variable, VAL> ValueOrderingStrategy<VAR, VAL> lcv() { return new LcvHeuristic<>();}

    /**
     * Implements the minimum-remaining-values heuristic.
     * @param <VAR> Type which is used to represent variables
     * @param <VAL> Type which is used to represent the values in the domains
     * @return List of variables
     */
    public static class MrvHeuristic<VAR extends Variable, VAL> implements VariableSelectionStrategy<VAR, VAL> {

        /** Returns variables from <code>vars</code> which are the best with respect to MRV. */
        public List<VAR> apply(CSP<VAR, VAL> csp, List<VAR> vars) {
            List<VAR> result = new ArrayList<>(); // List of variables which are the best with respect to MRV.
            int minValues = Integer.MAX_VALUE; // Minimum number of values in the domain of a variable.
            for (VAR var : vars) { // Iterate over all variables.
                int values = csp.getDomain(var).size(); // Number of values in the domain of the variable.
                if (values < minValues) { // If the number of values is less than the minimum number of values.
                    result.clear(); // Clear the list of variables.
                    minValues = values; // Update the minimum number of values.
                }
                if (values == minValues) // If the number of values is equal to the minimum number of values.
                    result.add(var); // Add the variable to the list of variables.
            }
            return result;
        }
    }

    /**
     * Implements the degree heuristic. Constraints with arbitrary scope size are supported.
     */
    public static class DegHeuristic<VAR extends Variable, VAL> implements VariableSelectionStrategy<VAR, VAL> {

        /** Returns variables from <code>vars</code> which are the best with respect to DEG. */
        public List<VAR> apply(CSP<VAR, VAL> csp, List<VAR> vars) {
            List<VAR> result = new ArrayList<>(); // List of variables which are the best with respect to DEG.
            int maxDegree = -1; // Maximum degree of a variable.
            for (VAR var : vars) { // Iterate over all variables.
                int degree = csp.getConstraints(var).size(); // Degree of the variable.
                if (degree > maxDegree) { // If the degree is greater than the maximum degree.
                    result.clear(); // Clear the list of variables.
                    maxDegree = degree; // Update the maximum degree.
                }
                if (degree == maxDegree) // If the degree is equal to the maximum degree.
                    result.add(var); // Add the variable to the list of variables.
            }
            return result;
        }
    }

    /**
     * Implements the least constraining value heuristic.
     */
    public static class LcvHeuristic<VAR extends Variable, VAL> implements ValueOrderingStrategy<VAR, VAL> {

        /** Returns the values of Dom(var) in a special order. The least constraining value comes first. */
        public List<VAL> apply(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var) {
            List<Pair<VAL, Integer>> pairs = new ArrayList<>(); // List of pairs of values and the number of lost values.
            for (VAL value : csp.getDomain(var)) { // Iterate over all values in the domain of the variable.
                int num = countLostValues(csp, assignment, var, value); // Number of lost values.
                pairs.add(new Pair<>(value, num)); // Add the pair of value and number of lost values to the list.
            }
            // Sort the pairs by the number of lost values and return the values in the order of the pairs.
            return pairs.stream().sorted(Comparator.comparing(Pair::getSecond)).map(Pair::getFirst)
                    .collect(Collectors.toList());
        }

        /**
         * Ignores constraints which are not binary.
         */
        private int countLostValues(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var, VAL value) {
            int result = 0; // Number of lost values.
            Assignment<VAR, VAL> assign = new Assignment<>(); // Assignment of variables to values.
            assign.add(var, value); // Add the variable and value to the assignment.
            for (Constraint<VAR, VAL> constraint : csp.getConstraints(var)) { // Iterate over all constraints of the variable.
                if (constraint.getScope().size() == 2) { // If the scope of the constraint is binary.
                    VAR neighbor = csp.getNeighbor(var, constraint); // Get the neighbor variable of the constraint.
                    if (!assignment.contains(neighbor)) // If the neighbor variable is not in the assignment.
                        for (VAL nValue : csp.getDomain(neighbor)) { // Iterate over all values in the domain of the neighbor variable.
                            assign.add(neighbor, nValue); // Add the neighbor variable and value to the assignment.
                            if (!constraint.isSatisfiedWith(assign)) // If the constraint is not satisfied with the assignment.
                                ++result;
                        }
                }
            }
            return result;
        }
    }
}