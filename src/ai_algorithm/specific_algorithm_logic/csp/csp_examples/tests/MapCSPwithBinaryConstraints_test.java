package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.MapCSPwithBinaryConstraints;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspListener;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.FlexibleBacktrackingSolver;

/**
 * MapCSPwithBinaryConstraints_test is a test class for MapCSPwithBinaryConstraints.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */

public class MapCSPwithBinaryConstraints_test {

    public static void main(String[] args) {

        MapCSPwithBinaryConstraints mapCSP = new MapCSPwithBinaryConstraints(); // create a new map CSP with binary constraints
        CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>(); // create a new step counter
        CspSolver<Variable, String> solver; // create a new CSP solver

        solver = new FlexibleBacktrackingSolver<>(); // create a new backtracking solver
        solver.addCspListener(stepCounter); // add a CSP listener to the solver
        stepCounter.reset();
        System.out.println("Map Coloring (Backtracking)");
        System.out.println(solver.solve(mapCSP)); // solve the map CSP problem
        System.out.println(stepCounter.getResults() + "\n");

        solver = new FlexibleBacktrackingSolver<Variable, String>().setAll(); // create a new backtracking solver with all heuristics
        solver.addCspListener(stepCounter); // add a CSP listener to the solver
        stepCounter.reset();
        System.out.println("Map Coloring (Backtracking + MRV & DEG + LCV + AC3)");
        System.out.println(solver.solve(mapCSP)); // solve the map CSP problem with all heuristics
        System.out.println(stepCounter.getResults() + "\n");

    }

}
