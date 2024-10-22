package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference.InferenceLog;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.Tasks;

import java.util.Optional;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/AbstractBacktrackingSolver.java
 *
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.5, Page 215.<br>
 * <br>
 * <p>
 * <pre>
 * <code>
 * function BACKTRACKING-SEARCH(csp) returns a solution, or failure
 *    return BACKTRACK({ }, csp)
 *
 * function BACKTRACK(assignment, csp) returns a solution, or failure
 *    if assignment is complete then return assignment
 *    var = SELECT-UNASSIGNED-VARIABLE(csp)
 *    for each value in ORDER-DOMAIN-VALUES(var, assignment, csp) do
 *       if value is consistent with assignment then
 *          add {var = value} to assignment
 *          inferences = INFERENCE(csp, var, value)
 *          if inferences != failure then
 *             add inferences to assignment
 *             result = BACKTRACK(assignment, csp)
 *             if result != failure then
 *                return result
 *          remove {var = value} and inferences from assignment
 *    return failure
 * </code>
 * </pre>
 * <p>
 * Figure 6.5 A simple backtracking algorithm for constraint satisfaction
 * problems. The algorithm is modeled on the recursive depth-first search of
 * Chapter 3. By varying the functions SELECT-UNASSIGNED-VARIABLE and
 * ORDER-DOMAIN-VALUES, we can implement the general-purpose heuristic discussed
 * in the text. The function INFERENCE can optionally be used to impose arc-,
 * path-, or k-consistency, as desired. If a value choice leads to failure
 * (noticed wither by INFERENCE or by BACKTRACK), then value assignments
 * (including those made by INFERENCE) are removed from the current assignment
 * and a new value is tried.
 *
 * @param <VAR> Type which is used to represent variables
 * @param <VAL> Type which is used to represent the values in the domains
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public abstract class AbstractBacktrackingSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {

    /** Applies a recursive backtracking search to solve the CSP. */
    public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
        Assignment<VAR, VAL> result = backtrack(csp, new Assignment<>()); // Start with an empty assignment.
        return result != null ? Optional.of(result) : Optional.empty(); // Return the result if it is not null.
    }

    /**
     * Template method, which can be configured by overriding the three
     * primitive operations below.
     * @return An assignment (possibly incomplete if task was cancelled) or null if no solution was found.
     */
    private Assignment<VAR, VAL> backtrack(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment) {
        Assignment<VAR, VAL> result = null; // Initialize the result to null.
        if (assignment.isComplete(csp.getVariables()) || Tasks.currIsCancelled()) {
            result = assignment;
        } else {
            VAR var = selectUnassignedVariable(csp, assignment); // Select an unassigned variable.
            for (VAL value : orderDomainValues(csp, assignment, var)) { // Order the domain values of the variable.
                assignment.add(var, value); // Add the variable and its value to the assignment.
                fireStateChanged(csp, assignment, var); // Fire a state change event with the CSP, assignment, and variable.
                if (assignment.isConsistent(csp.getConstraints(var))) { // If the assignment is consistent.
                    InferenceLog<VAR, VAL> log = inference(csp, assignment, var); // Perform inference with the CSP, assignment, and variable.
                    if (!log.isEmpty()) // If the log is not empty.
                        fireStateChanged(csp, null, null); // Fire a state change event with the CSP, null, and null.
                    if (!log.inconsistencyFound()) { // If no inconsistency was found.
                        result = backtrack(csp, assignment); // Recursively call the backtrack method with the CSP and assignment.
                        if (result != null) // If the result is not null.
                            break;
                    }
                    log.undo(csp);
                }
                assignment.remove(var); // Remove the variable from the assignment.
            }
        }
        return result;
    }

    /**
     * Primitive operation, selecting a not yet assigned variable.
     */
    protected abstract VAR selectUnassignedVariable(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment);

    /**
     * Primitive operation, ordering the domain values of the specified variable.
     */
    protected abstract Iterable<VAL> orderDomainValues(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);

    /**
     * Primitive operation, which tries to optimize the CSP representation with respect to a new assignment.
     *
     * @param var The variable which just got a new value in the assignment.
     * @return An object which provides information about
     * (1) whether changes have been performed,
     * (2) possibly inferred empty domains, and
     * (3) how to restore the original CSP.
     */
    protected abstract InferenceLog<VAR, VAL> inference(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var);
}
