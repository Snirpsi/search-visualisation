package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.AllDiffConstraint;
import ai_algorithm.specific_algorithm_logic.csp.Values;
import ai_algorithm.specific_algorithm_logic.csp.Variables;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Assignment;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

/**
 * Represents a constraint that requires all variables to have different values.
 *
 * @author Alexander
 */

public class AllDiffConstraint_test {

    public static void main(String[] args) {

        /**
         * Create a new assignment with the following values:
         * WA = BLUE
         * NT = RED
         * SA = GREEN
         *
         * !!! This assignment has been created for testing purposes only !!!
         */
        Assignment<Variable, String> assignment = new Assignment(); // create a new assignment
        assignment.add(Variables.WA, Values.BLUE); // add a variable WA with a value BLUE to the assignment
        assignment.add(Variables.NT, Values.RED); // add a variable NT with a value RED to the assignment
        assignment.add(Variables.SA, Values.GREEN); // add a variable SA with a value GREEN to the assignment

        Assignment<Variable, String> nonConsistentAssignment = assignment.clone(); // clone the assignment
        nonConsistentAssignment.add(Variables.NSW, Values.RED); // add a variable NSW with a value RED to the assignment

        AllDiffConstraint alldiff = new AllDiffConstraint(Variables.list);

        // Print the result of the constraint satisfaction alldiff.isSatisfiedWith(assignment) = Objects do not match the same value as other nodes -> TRUE
        // Print the result of the constraint satisfaction alldiff.isSatisfiedWith(nonConsistentAssignment) = Objects match the same value as other nodes -> FALSE
        System.out.println("(Constraint is satisfied with the assignment: result = TRUE, non-consistent is statisfied with the assignment: result = FALSE)");
        System.out.println("(" + alldiff.isSatisfiedWith(assignment) + ", " + alldiff.isSatisfiedWith(nonConsistentAssignment) + ")");

    }

}
