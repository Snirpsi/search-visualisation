package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.*;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.framework.QueueFactory;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/inference/AC3Strategy.java
 *
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.3, Page 209.<br>
 * <br>
 *
 * <pre>
 * <code>
 * function AC-3(csp) returns false if an inconsistency is found and true otherwise
 *    inputs: csp, a binary CSP with components (X, D, C)
 *    local variables: queue, a queue of arcs, initially all the arcs in csp
 *    while queue is not empty do
 *       (Xi, Xj) = REMOVE-FIRST(queue)
 *       if REVISE(csp, Xi, Xj) then
 *          if size of Di = 0 then return false
 *             for each Xk in Xi.NEIGHBORS - {Xj} do
 *                add (Xk, Xi) to queue
 *    return true
 *
 * function REVISE(csp, Xi, Xj) returns true iff we revise the domain of Xi
 *    revised = false
 *    for each x in Di do
 *       if no value y in Dj allows (x ,y) to satisfy the constraint between Xi and Xj then
 *          delete x from Di
 *          revised = true
 *    return revised
 * </code>
 * </pre>
 *
 * Figure 6.3 The arc-consistency algorithm AC-3. After applying AC-3, either
 * every arc is arc-consistent, or some variable has an empty domain, indicating
 * that the CSP cannot be solved. The name "AC-3" was used by the algorithm's
 * inventor (Mackworth, 1977) because it's the third version developed in the
 * paper.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class AC3Strategy<VAR extends Variable, VAL> implements InferenceStrategy<VAR, VAL> {

    /**
     * Makes a CSP consisting of binary constraints arc-consistent.
     *
     * @param csp The CSP which should be checked for consistency.
     * @return An object which indicates success/failure and contains data to
     *         undo the operation.
     */
    public InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp) {
        Queue<VAR> queue = QueueFactory.createFifoQueueNoDuplicates(); // FIFO queue with no duplicates
        queue.addAll(csp.getVariables()); // Add all variables from the csp to the queue
        DomainLog<VAR, VAL> log = new DomainLog<>(); // Create a new DomainLog object with variables and values
        reduceDomains(queue, csp, log); // Reduce the domains of the variables in the queue
        return log.compactify(); // Return the compactified log
    }

    /**
     * Reduces the domain of the specified variable to the specified value and
     * reestablishes arc-consistency. It is assumed that the provided CSP was
     * arc-consistent before the call.
     *
     * @param csp The CSP which should be checked for consistency.
     * @param assignment The assignment which has been extended by a value assignment for <code>var</code>.
     * @param var The variable which has been assigned a value.
     * @return An object which indicates success/failure and contains data topow
     *         undo the operation.
     */
    public InferenceLog<VAR, VAL> apply(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR var) {
        Domain<VAL> domain = csp.getDomain(var); // Get the domain of the variable
        VAL value = assignment.getValue(var); // Get the value of the variable
        assert domain.contains(value); // Check if the domain contains the value
        DomainLog<VAR, VAL> log = new DomainLog<>(); // Create a new DomainLog object with variables and values
        if (domain.size() > 1) { // If the domain size is greater than 1
            Queue<VAR> queue = QueueFactory.createFifoQueue(); // Create a FIFO queue
            queue.add(var); // Add the variable var to the queue
            log.storeDomainFor(var, domain); // Store the domain for the variable var
            csp.setDomain(var, new Domain<>(value)); // Set the domain of the variable var to the value
            reduceDomains(queue, csp, log); // Reduce the domains of the variables in the queue
        }
        return log.compactify(); // Return the compactified log
    }

    /**
     * For efficiency reasons the queue manages updated variables vj whereas the original AC3
     * manages neighbor arcs (vi, vj). Constraints which are not binary are ignored.
     */
    private void reduceDomains(Queue<VAR> queue, CSP<VAR, VAL> csp, DomainLog<VAR, VAL> log) {
        while (!queue.isEmpty()) { // While the queue is not empty
            VAR var = queue.remove(); // Remove the first element from the queue
            for (Constraint<VAR, VAL> constraint : csp.getConstraints(var)) { // For each constraint in the constraints of the variable var
                VAR neighbor = csp.getNeighbor(var, constraint); // Get the neighbor of the variable var
                if (neighbor != null && revise(neighbor, var, constraint, csp, log)) { // If the neighbor is not null and the domain of the variable was revised
                    if (csp.getDomain(neighbor).isEmpty()) { // If the domain of the neighbor is empty
                        log.setEmptyDomainFound(true); // Set the empty domain flag to true
                        return;
                    }
                    queue.add(neighbor); // Add the neighbor to the queue
                }
            }
        }
    }

    /**
     * Establishes arc-consistency for (xi, xj).
     *
     * @param xi The first variable
     * @param xj The second variable
     * @param constraint The constraint between the two variables
     * @param csp The CSP which should be checked for consistency.
     * @param log The log which stores the domain information
     * @return value true if the domain of xi was reduced.
     */
    private boolean revise(VAR xi, VAR xj, Constraint<VAR, VAL> constraint,
                           CSP<VAR, VAL> csp, DomainLog<VAR, VAL> log) {
        Domain<VAL> currDomain = csp.getDomain(xi); // Get the current domain of the variable xi
        List<VAL> newValues = new ArrayList<>(currDomain.size()); // Create a new list of values with name newValues
        Assignment<VAR, VAL> assignment = new Assignment<>(); // Create a new assignment
        for (VAL vi : currDomain) { // For each value vi in the current domain
            assignment.add(xi, vi); // Adds a variable-value pair to this assignment (variable = Xi) (value = vi).
            for (VAL vj : csp.getDomain(xj)) { // For each value vj in the domain of the variable xj
                assignment.add(xj, vj); // Adds a variable-value pair to this assignment (variable = Xj) (value = vj).
                if (constraint.isSatisfiedWith(assignment)) { // If the constraint is satisfied with the assignment
                    newValues.add(vi); // Add the value vi to the newValues list
                    break;
                }
            }
        }
        if (newValues.size() < currDomain.size()) { // If the newValues list is smaller than the current domain size
            log.storeDomainFor(xi, csp.getDomain(xi)); // Store the domain for the variable xi in the log
            csp.setDomain(xi, new Domain<>(newValues)); // Set the domain of the variable xi to the newValues list
            return true;
        }
        return false;
    }
}