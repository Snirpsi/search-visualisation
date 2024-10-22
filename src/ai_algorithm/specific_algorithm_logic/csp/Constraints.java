package ai_algorithm.specific_algorithm_logic.csp;

import static ai_algorithm.specific_algorithm_logic.csp.Variables.*;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides a list of constraints for the map coloring problem.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class Constraints {

    // The list of constraints for the map coloring problem.
    public static final List<AllDiffConstraint> list = Arrays.asList(
            new AllDiffConstraint(Arrays.asList(WA, NT, SA)), // AllDiffConstraint for the variables WA, NT, SA
            new AllDiffConstraint(Arrays.asList(NT, SA, Q)), // AllDiffConstraint for the variables NT, SA, Q
            new AllDiffConstraint(Arrays.asList(SA, Q, NSW)), // AllDiffConstraint for the variables SA, Q, NSW
            new AllDiffConstraint(Arrays.asList(SA, NSW, V)) // AllDiffConstraint for the variables SA, NSW, V
    );

    // Private constructor to prevent instantiation of this class.
    private Constraints() {}

}



