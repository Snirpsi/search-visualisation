package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.SearchNode;
/**
 *
 * @author Tizian Dippold (tizian.dippold@tum.de)
 *
 */
public class RecursiveDepthSearch extends SearchAlgorithm {

    @Override
    public SearchNode search() {
        SearchNode start = new SearchNode(null, this.problem.getInitialState(), 0, null);
        ExploredSet explored = new ExploredSet();
        return recursiveSearch(start, explored);
    }

    private SearchNode recursiveSearch(SearchNode s, ExploredSet explored) {
        if (problem.isGoalState(s.getState())) {
            return s;
        }
        explored.add(s);
        for (SearchNode currentChild : s.expand()) {
            if (!explored.contains(currentChild.getState())) {
                SearchNode recursiveErg = recursiveSearch(currentChild, explored);
                if (recursiveErg != null) {
                    return recursiveErg;
                }
            }
        }
        return null;
    }

}
