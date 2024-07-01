package ai_algorithm.problems.mapColoring.general;

import ai_algorithm.problems.mapColoring.AbstractMapColoringProblem;
import ai_algorithm.problems.mapColoring.AbstractMapColoringState;

import java.util.List;
import java.util.Map;

public class MapColoringStateGeneral extends AbstractMapColoringState {

    /**
     * initializes state with problem and assignments
     *
     * @param problem
     * @param domain
     * @param assignments
     */
    public MapColoringStateGeneral(AbstractMapColoringProblem problem, Map<String, List<String>> domain, Map<String, String> assignments) {
        super(problem, domain, assignments);
    }

}
