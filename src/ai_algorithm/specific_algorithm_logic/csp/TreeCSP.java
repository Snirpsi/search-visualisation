package ai_algorithm.specific_algorithm_logic.csp;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.CSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Domain;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_examples.NotEqualConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * TreeCSP is a CSP problem with binary constraints.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class TreeCSP extends CSP<Variable, String> {

    public static final Variable WA = new Variable("WA");
    public static final Variable NT = new Variable("NT");
    public static final Variable Q = new Variable("Q");
    public static final Variable NSW = new Variable("NSW");
    public static final Variable V = new Variable("V");

    // create a list of variables for the CSP problem
    public static final List<Variable> variableList = Arrays.asList(WA, NT, Q, NSW, V);

    // the following lines declare the domain values for the CSP problem
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";

    // create a list of common domain values for the CSP problem variables
    private static final List<String> commonDomain = Arrays.asList(Values.RED, Values.GREEN, Values.BLUE);
    // create a domain for each variable in the CSP problem (WA, NT, Q, NSW, V)
    public static final Domain<String> domainWA = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> domainNT = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> domainQ = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> domainNSW = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> domainV = new Domain(new ArrayList<String>(commonDomain));

    // create a list of domains for the CSP problem variables
    public static final List<Domain> domainList = Arrays.asList(domainWA, domainNT, domainQ, domainNSW, domainV);

    // create a list of constraints for the CSP problem
    public static final List<NotEqualConstraint> constraintList = Arrays.asList(
            new NotEqualConstraint<>(WA, NT), // WA != NT
            new NotEqualConstraint<>(NT, Q), // NT != Q
            new NotEqualConstraint<>(Q, NSW), // Q != NSW
            new NotEqualConstraint<>(NSW, V) // NSW != V
    );

    /**
     * Constructor for TreeCSP.
     */
    public TreeCSP() {
        super(variableList); // create a new CSP with the list of variables
        Iterator<Domain> domainIter = domainList.listIterator(); // create a new iterator for the list of domains
        variableList.forEach( // for each variable in the list of variables
                variable -> super.setDomain(variable, domainIter.next()) // set the domain of the variable to the next domain in the list of domains
        );
        constraintList.forEach(super::addConstraint); // add each constraint in the list of constraints to the CSP
    }
}