package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.TreeCSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspListener;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.TreeCspSolver;

/**
 * This class tests the TreeCSP problem.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */

public class TreeCSP_test {

    public static void main(String[] args) {

        TreeCSP treeCSP = new TreeCSP(); // This is the CSP problem
        CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>(); // This is a step counter
        CspSolver<Variable, String> solver = new TreeCspSolver<>(); // This is the CSP solver
        solver.addCspListener(stepCounter); // Add the step counter to the solver
        stepCounter.reset(); // Reset the step counter
        System.out.println("Map Coloring (Tree CSP)");
        System.out.println(solver.solve(treeCSP)); // Solve the CSP problem
        System.out.println(stepCounter.getResults() + "\n");

    }

}
