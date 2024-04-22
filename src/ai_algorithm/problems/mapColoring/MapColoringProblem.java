package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;

import java.util.List;

public class MapColoringProblem extends Problem {

    // TODO: Implement the MapColoringProblem

    @Override
    public State getInitialState() {
        return null;
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