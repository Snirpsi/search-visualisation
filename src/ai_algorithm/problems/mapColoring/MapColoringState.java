package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;

import java.util.*;

public class MapColoringState extends State {

    private MapColoringProblem problem;
    private Map<String, String> assignments;

    public MapColoringState(MapColoringProblem problem, Map<String, String> assignments) {
        this.problem = problem;
        this.assignments = assignments;
    }

    @Override
    public Problem getProblem() {
        return this.problem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapColoringState other = (MapColoringState) obj;
        return Objects.equals(assignments, other.assignments);
    }
}
