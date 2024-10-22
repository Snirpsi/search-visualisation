package ai_algorithm.specific_algorithm_logic.csp;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Domain;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

import java.util.Iterator;

/**
 * MapCSPwithBinaryConstraints is a CSP problem with binary constraints.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */

public class MapCSPwithBinaryConstraints extends CSP<Variable, String> {

    /**
     * Constructor for MapCSPwithBinaryConstraints.
     */
    public MapCSPwithBinaryConstraints() {
        super(Variables.list); // create a new CSP with the list of variables
        Iterator<Domain> domainIter = Domains.list.listIterator(); // create a new iterator for the list of domains
        Variables.list.forEach( // for each variable in the list of variables
                // set the domain of the variable to the next domain in the list of domains
                variable -> super.setDomain(variable, domainIter.next())
        );
        // add each constraint in the list of binary constraints to the CSP
        BinaryConstraints.list.forEach(super::addConstraint);
    }

}