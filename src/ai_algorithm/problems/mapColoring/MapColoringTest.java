package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.specific_algorithm_logic.csp.csp_content.Variable;

public class MapColoringTest {

    public static void main(String[] args) {

        // MapColoringProblem Tests
        MapColoringProblem problem = new MapColoringProblem();

        System.out.println(problem.GAMESIZE);
        System.out.println("Initial state: " + problem.getInitialState());
        System.out.println("Goal state: " + problem.isGoalState(problem.getInitialState()));
        System.out.println("Variables: " + problem.getVariables());
        System.out.println("Constraints: " + problem.getConstraints());
        System.out.println("Domain: " + problem.getDomain());
        System.out.println("Domain of Variable: " + problem.getDomainOfVariable("V"));
        System.out.println("Assignments: " + problem.getAssignments());
        System.out.println("Arcs: " + problem.getVariableConstraintsMap());

        // MapColoringState Tests
        System.out.println("\n\n");
        MapColoringState state = new MapColoringState(problem, problem.getAssignments());

        System.out.println(state.getProblem());
        System.out.println(state.getAssignments());
        System.out.println(state.hashCode());
        System.out.println(state.equals("A1"));
        System.out.println(state.toString());

        problem.runAC3();
        System.out.println("Initial state: " + problem.getInitialState());
        System.out.println("Assignments: " + problem.getAssignments());
        System.out.println("Arcs GetArcs after runAC3: " + problem.getVariableConstraintsMap());
        System.out.println("\nFinal assignments: " + problem.getAssignments());

    }




}
