package ai_algorithm.specific_algorithm_logic.csp;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Domain;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * This class is a representation of the domains of the variables in the map coloring problem.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class Domains {
    // the domain is a list of colors (Strings) for the map coloring problem and the common domain for all variables
    private static final List<String> commonDomain = Arrays.asList(Values.RED, Values.GREEN, Values.BLUE);
    // Creation of a new ArrayList of strings for the Western Australia (WA) domain,
    // which in turn was filled with the values of commonDomain.
    // The same applies to the other variables.
    public static final Domain<String> WA = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> NT = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> Q = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> NSW = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> V = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> SA = new Domain(new ArrayList<String>(commonDomain));
    public static final Domain<String> T = new Domain(new ArrayList<String>(commonDomain));

    // In total, this list contains the domains for all variables in the map coloring problem modeled in this class.
    // Each domain represents the possible colors that can be assigned to a particular region on the map.
    public static final List<Domain> list = Arrays.asList(WA, NT, Q, NSW, V, SA, T);

    // Private constructor to prevent instantiation of this class.
    private Domains() {}

}