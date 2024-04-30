package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.*;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.inference.AC3Strategy;

import java.util.*;

/**
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class AC3Strategy_test {

    public static void main(String[] args) {

        Variable x = new Variable("X"); // create a new variable X
        Variable y = new Variable("Y"); // create a new variable Y
        CSP<Variable, Integer> csp = new CSP(Arrays.asList(x, y)); // create a new CSP with the variables x and y
        csp.setDomain(x, new Domain<Integer>(0,1,2,3)); // set the domain of the variable x to 0, 1, 2, 3
        csp.setDomain(y, new Domain<Integer>(0,1,4,9)); // set the domain of the variable y to 0, 1, 4, 9
        csp.addConstraint(new Constraint<Variable, Integer>() { // add a new constraint to the CSP
            /**
             * Returns the scope of the constraint.
             * @return a list of variables
             */
            public List<Variable> getScope() {
                return Arrays.asList(x, y); // return a list of variables x and y
            }
            /**
             * Returns true if the constraint is satisfied with the given assignment.
             * @param assignment the assignment
             * @return true if the constraint is satisfied
             */
            public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
                // return true if the value of the variable y is equal to the value of the variable x squared
                return assignment.getValue(y) == assignment.getValue(x) * assignment.getValue(x);
            }
        });

        // Let's apply the ac3 inference algorithms on this two variable csp
        AC3Strategy ac3Strategy = new AC3Strategy(); // create a new AC3Strategy
        ac3Strategy.apply(csp).isEmpty(); // apply the AC3Strategy on the CSP
        String line = new String(new char[34]).replace('\0', '-'); // create a new line
        System.out.println(line);
        System.out.format("| %-12S | %-15S |%n","variable","domain"); // print the variable and domain
        System.out.println(line);
        for (Variable var: csp.getVariables()) { // iterate through the variables of the CSP
            System.out.format("| %-12S | %-15S |%n", var.getName(),csp.getDomain(var)); // print the variable and its domain
        }
        System.out.println(line);

    }

}
