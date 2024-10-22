package ai_algorithm.specific_algorithm_logic.csp.csp_examples;

import ai_algorithm.specific_algorithm_logic.csp.Constraints;
import ai_algorithm.specific_algorithm_logic.csp.Domains;
import ai_algorithm.specific_algorithm_logic.csp.Variables;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Domain;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

import java.util.Iterator;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/csp/examples/MapCSP.java
 *
 * Represents the map coloring problem as a CSP.
 * The variables are the states of Australia and the values are the colors red, green and blue.
 * The constraints are that adjacent states must have different colors.
 * The map coloring problem is a classic example of CSPs.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */

public class MapCSP extends CSP<Variable, String> {

    /**
     * Constructor for the MapCSP class.
     * Creates a new map coloring problem.
     */
    public MapCSP() {
        // Call the constructor of the superclass CSP.
        super(Variables.list);
        // Create an iterator for the list of domains.
        Iterator<Domain> domainIter = Domains.list.listIterator();
        // For each variable in the list of variables:
        Variables.list.forEach(
                // Set the domain of the variable to the next domain in the list.
                variable -> super.setDomain(variable, domainIter.next())
        );
        // Add all constraints from the list of constraints to the CSP.
        Constraints.list.forEach(super::addConstraint);
    }

}



