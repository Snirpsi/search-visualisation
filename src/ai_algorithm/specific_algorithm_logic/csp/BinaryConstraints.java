package ai_algorithm.specific_algorithm_logic.csp;

import static ai_algorithm.specific_algorithm_logic.csp.Variables.*;

import ai_algorithm.specific_algorithm_logic.csp.csp_examples.NotEqualConstraint;
import java.util.List;
import java.util.Arrays;

/**
 * BinaryConstraints is a class that contains a list of NotEqualConstraint objects.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class BinaryConstraints {
    public static final List<NotEqualConstraint> list = Arrays.asList(
            new NotEqualConstraint<>(WA, NT), // Western Australia != Northern Territory
            new NotEqualConstraint<>(WA, SA), // Western Australia != South Australia
            new NotEqualConstraint<>(NT, SA), // Northern Territory != South Australia
            new NotEqualConstraint<>(NT, Q), // Northern Territory != Queensland
            new NotEqualConstraint<>(SA, Q), // South Australia != Queensland
            new NotEqualConstraint<>(SA, NSW), // South Australia != New South Wales
            new NotEqualConstraint<>(SA, V), // South Australia != Victoria
            new NotEqualConstraint<>(Q, NSW), // Queensland != New South Wales
            new NotEqualConstraint<>(NSW, V) // New South Wales != Victoria
    );

    // Private constructor to prevent instantiation
    private BinaryConstraints() {}
}