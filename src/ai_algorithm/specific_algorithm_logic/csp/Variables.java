package ai_algorithm.specific_algorithm_logic.csp;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the variables of the map coloring problem.
 * The variables are the states of Australia.
 * The variables are used in the map coloring example.
 * The variables are WA, NT, Q, NSW, V, SA, T.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class Variables {

    public static final Variable WA = new Variable("WA"); // the variable WA
    public static final Variable NT = new Variable("NT"); // the variable NT
    public static final Variable Q = new Variable("Q"); // the variable Q
    public static final Variable NSW = new Variable("NSW"); // the variable NSW
    public static final Variable V = new Variable("V"); // the variable V
    public static final Variable SA = new Variable("SA"); // the variable SA
    public static final Variable T = new Variable("T"); // the variable T

    public static final List<Variable> list = Arrays.asList(NSW, WA, NT, Q, SA, V, T); // list of all variables

    // Private constructor to prevent instantiation of this class.
    private Variables() {}

}