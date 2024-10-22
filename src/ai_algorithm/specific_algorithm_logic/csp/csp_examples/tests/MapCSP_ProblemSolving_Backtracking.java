package ai_algorithm.specific_algorithm_logic.csp.csp_examples.tests;

import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;
import ai_algorithm.specific_algorithm_logic.csp.csp_examples.MapCSP;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspListener;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.CspSolver;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.FlexibleBacktrackingSolver;

/**
 * Represents a map coloring problem solved by using backtracking.
 *
 * @author notebook.community "https://notebook.community/aimacode/aima-java/notebooks/ConstraintSatisfactionProblems"
 * @author Alexander (Comments adjusted)
 */
public class MapCSP_ProblemSolving_Backtracking {

    public static void main(String[] args) {

        MapCSP csp = new MapCSP(); // create a new map coloring problem with informations about the variables, domains and constraints
        CspSolver<Variable, String> solver = new FlexibleBacktrackingSolver<>(); // create a new flexible backtracking solver
        CspListener.StepCounter<Variable, String> stepCounter = new CspListener.StepCounter<>(); // create a new step counter for the backtracking solver
        solver.addCspListener(stepCounter); // add the step counter to the backtracking solver to count the steps of the backtracking algorithm (backtracking algorithm is a recursive algorithm)
        stepCounter.reset(); // reset the step counter to 0 (initial value) before solving the map coloring problem with the backtracking algorithm
        System.out.println("Map Coloring (Backtracking)");
        System.out.println(solver.solve(csp)); // solve the map coloring problem with the backtracking algorithm and print the result
        System.out.println(stepCounter.getResults() + "\n"); // print the number of steps the backtracking algorithm needed to solve the map coloring problem

    }

}





