package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_examples.MapCSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspListener;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.MinConflictsSolver;

/**
 * Represents a constraint that requires all variables to have different values.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class MapCSP_MinimumConflicts {

    public static void main(String[] args) {

        MapCSP mapCSP = new MapCSP(); // create a new map CSP
        CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>(); // create a new step counter
        CspSolver<Variable, String> solver = new MinConflictsSolver<>(1000); // create a new minimum conflicts solver with maximum 1000 steps
        solver.addCspListener(stepCounter); // add a CSP listener to the solver
        stepCounter.reset(); // reset the step counter
        System.out.println("Map Coloring (Minimum Conflicts)");
        System.out.println(solver.solve(mapCSP)); // solve the map CSP
        System.out.println(stepCounter.getResults() + "\n");

    }

}
