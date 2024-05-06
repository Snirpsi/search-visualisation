package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;

import java.util.*;

public class MapColoringProblem extends Problem {

    static List<String> variables;
    static List<List<String>> constraints;
    static List<List<String>> d;
    static Map<String, String> assignments;
    static List<List<String>> arcs;

    public MapColoringProblem() {
        super();
        this.variables = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");
        this.constraints = Arrays.asList(
                Arrays.asList("NT", "SA"),
                Arrays.asList("WA", "SA", "Q"),
                Arrays.asList("WA", "NT", "Q"),
                Arrays.asList("SA", "NT", "NSW"),
                Arrays.asList("SA", "Q", "V"),
                Arrays.asList("NSW", "SA"),
                Arrays.asList("T"),
                Collections.emptyList()
        );
        this.d = Arrays.asList(
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue"))
        );
        this.assignments = new HashMap<>();
        this.arcs = new ArrayList<>();
    }

    @Override
    public State getInitialState() {
        return new MapColoringState(this, assignments);
    }

    @Override
    public boolean isGoalState(State state) {
        return false;
    }

    @Override
    public List<String> getActions(State state) {
        return null;
    }

    @Override
    public State getSuccessor(State state, String action) {
        return null;
    }

    @Override
    public double getCost(State state, String action, State succ) {
        return 0;
    }
}
