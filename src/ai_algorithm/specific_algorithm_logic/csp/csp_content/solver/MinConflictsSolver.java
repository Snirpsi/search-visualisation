package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Constraint;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.Tasks;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.Util;

import java.util.*;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/MinConflictsSolver.java
 *
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.8, Page 221.<br>
 * <br>
 *
 * <pre>
 * <code>
 * function MIN-CONFLICTS(csp, max-steps) returns a solution or failure
 *    inputs: csp, a constraint satisfaction problem
 *            max-steps, the number of steps allowed before giving up
 *    current = an initial complete assignment for csp
 *    for i = 1 to max steps do
 *       if current is a solution for csp then return current
 *       var = a randomly chosen conflicted variable from csp.VARIABLES
 *       value = the value v for var that minimizes CONFLICTS(var, v, current, csp)
 *       set var = value in current
 *    return failure
 * </code>
 * </pre>
 *
 * Figure 6.8 The MIN-CONFLICTS algorithm for solving CSPs by local search. The
 * initial state may be chosen randomly or by a greedy assignment process that
 * chooses a minimal-conflict value for each variable in turn. The CONFLICTS
 * function counts the number of constraints violated by a particular value,
 * given the rest of the current assignment.
 *
 * @param <VAR> Type which is used to represent variables
 * @param <VAL> Type which is used to represent the values in the domains
 *
 * @author Ruediger Lunde
 * @author Mike Stampone
 * @author Alexander (Comments adjusted)
 */
public class MinConflictsSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {
    private int maxSteps;

    /**
     * Constructs a min-conflicts strategy with a given number of steps allowed
     * before giving up.
     *
     * @param maxSteps
     *            the number of steps allowed before giving up
     */
    public MinConflictsSolver(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    /**
     * Solves the given CSP by using the min-conflicts algorithm.
     *
     * @param csp a CSP to be solved.
     * @return the computed solution or empty if no solution was found.
     */
    public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
        Assignment<VAR, VAL> current = generateRandomAssignment(csp); // Generate a random assignment for the given CSP
        fireStateChanged(csp, current, null); // Call the fireStateChanged method to notify the listeners about the state change
        for (int i = 0; i < maxSteps && !Tasks.currIsCancelled(); i++) { // Iterate over the maximum number of steps
            if (current.isSolution(csp)) {
                return Optional.of(current);// If the current assignment is a solution, return it
            } else {
                Set<VAR> vars = getConflictedVariables(current, csp); // Get the set of variables which are in conflict with the current assignment
                VAR var = Util.selectRandomlyFromSet(vars); // Select a random variable from the set of conflicted variables
                VAL value = getMinConflictValueFor(var, current, csp); // Get a value for the specified variable which minimizes the number of conflicts
                current.add(var, value); // Add the value to the current assignment
                fireStateChanged(csp, current, var); // Call the fireStateChanged method to notify the listeners about the state change
            }
        }
        return Optional.empty(); // Return empty if no solution was found
    }

    /**
     * Generates a random assignment for the given CSP.
     *
     * @param csp a CSP for which the assignment should be generated.
     * @return a random assignment for the given CSP.
     */
    private Assignment<VAR, VAL> generateRandomAssignment(CSP<VAR, VAL> csp) {
        Assignment<VAR, VAL> result = new Assignment<>(); // Create a new assignment
        for (VAR var : csp.getVariables()) { // Iterate over the variables of the CSP
            VAL randomValue = Util.selectRandomlyFromList(csp.getDomain(var).asList()); // Select a random value from the domain of the variable
            result.add(var, randomValue); // Add the variable and the value to the assignment
        }
        return result;
    }

    /**
     * Returns a set of variables which are in conflict with the current assignment.
     *
     * @param assignment the current assignment.
     * @return a set of variables which are in conflict with the current assignment.
     */
    private Set<VAR> getConflictedVariables(Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
        Set<VAR> result = new LinkedHashSet<>(); // Create a new set to store the conflicted variables
        csp.getConstraints().stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).
                map(Constraint::getScope).forEach(result::addAll); // Add the variables which are in conflict with the current assignment to the set
        return result;
    }

    /**
     * Returns a value for the specified variable which minimizes the number of conflicts.
     *
     * @param var the variable for which a value should be selected.
     * @return a value for the specified variable which minimizes the number of conflicts.
     */
    private VAL getMinConflictValueFor(VAR var, Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
        List<Constraint<VAR, VAL>> constraints = csp.getConstraints(var); // Get the constraints for the specified variable
        Assignment<VAR, VAL> testAssignment = assignment.clone(); // Clone the current assignment
        int minConflict = Integer.MAX_VALUE; // Initialize the minimum number of conflicts
        List<VAL> resultCandidates = new ArrayList<>(); // Create a list to store the result candidates
        for (VAL value : csp.getDomain(var)) { // Iterate over the domain of the values of the variable
            testAssignment.add(var, value); // Add the value to the test assignment
            int currConflict = countConflicts(testAssignment, constraints); //
            if (currConflict <= minConflict) {
                if (currConflict < minConflict) {
                    resultCandidates.clear(); // Clear the result candidates
                    minConflict = currConflict; // Update the minimum number of conflicts
                }
                resultCandidates.add(value); // Add the value to the result candidates
            }
        }
        return (!resultCandidates.isEmpty()) ? Util.selectRandomlyFromList(resultCandidates) : null;
    }

    /**
     * Counts the number of constraints which are not satisfied by the given assignment.
     *
     * @param assignment the assignment to be checked.
     * @return the number of constraints which are not satisfied by the given assignment.
     */
    private int countConflicts(Assignment<VAR, VAL> assignment, List<Constraint<VAR, VAL>> constraints) {
        return (int) constraints.stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).count();
    }
}