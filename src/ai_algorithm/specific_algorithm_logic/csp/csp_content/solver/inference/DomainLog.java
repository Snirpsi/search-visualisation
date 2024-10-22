package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Domain;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.util.Pair;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/solver/inference/DomainLog.java
 *
 * Provides information which might be useful for a caller of a constraint
 * propagation algorithm. It maintains old domains for variables and provides
 * means to restore the initial state of the CSP (before domain reduction
 * started). Additionally, a flag indicates whether an empty domain has been
 * found during propagation.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class DomainLog<VAR extends Variable, VAL> implements InferenceLog<VAR, VAL> {
    private List<Pair<VAR, Domain<VAL>>> savedDomains; // List of saved domains
    private HashSet<VAR> affectedVariables; // Set of affected variables
    private boolean emptyDomainObserved; // Flag indicating whether an empty domain has been found

    /**
     * Initializes an empty domain log.
     */
    public DomainLog() {
        savedDomains = new ArrayList<>(); // Create a new list of saved domains
        affectedVariables = new HashSet<>(); // Create a new set of affected variables
    }

    /**
     * Clears the domain log.
     */
    public void clear() {
        savedDomains.clear(); // Clear the list of saved domains
        affectedVariables.clear(); // Clear the set of affected variables
    }

    /**
     * Stores the specified domain for the specified variable if a domain has
     * not yet been stored for the variable.
     */
    public void storeDomainFor(VAR var, Domain<VAL> domain) {
        if (!affectedVariables.contains(var)) { // If the variable var is not in the set
            savedDomains.add(new Pair<>(var, domain)); // Add the variable var and its domain to the list
            affectedVariables.add(var); // Add the variable var to the set
        }
    }

    /**
     * Sets the empty domain flag.
     */
    public void setEmptyDomainFound(boolean b) {
        emptyDomainObserved = b; // Set the empty domain flag to b
    }

    /**
     * Can be called after all domain information has been collected to reduce
     * storage consumption.
     *
     * @return this object, after removing one hashtable.
     */
    public DomainLog<VAR, VAL> compactify() {
        affectedVariables = null; // Set the affected variables to null
        return this;
    }

    @Override
    public boolean isEmpty() {
        return savedDomains.isEmpty(); // Return true if the list of saved domains is empty
    }

    @Override
    public void undo(CSP<VAR, VAL> csp) {
        getSavedDomains().forEach(pair -> csp.setDomain(pair.getFirst(), pair.getSecond()));
    }

    @Override
    public boolean inconsistencyFound() {
        return emptyDomainObserved; // Return true if an empty domain has been observed
    }

    private List<Pair<VAR, Domain<VAL>>> getSavedDomains() {
        return savedDomains; // Return the list of saved domains
    }

    /**
     * @return A string representation of the domain log.
     */
    public String toString() {
        StringBuilder result = new StringBuilder(); // Create a new StringBuilder object with name result
        for (Pair<VAR, Domain<VAL>> pair : savedDomains) // For each pair in the list of saved domains
            // Append the variable and its domain to the result
            result.append(pair.getFirst()).append("=").append(pair.getSecond()).append(" ");
        if (emptyDomainObserved) // If an empty domain has been observed
            result.append("!"); // Append an exclamation mark to the result
        return result.toString(); // Return the result as a string
    }
}