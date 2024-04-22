package ai_algorithm.problems.mapColoring;

import ai_algorithm.Path;
import ai_algorithm.SearchNode;

public class MapColoringPath extends Path {

    /**
     * The Constructor is used to get the Path from the initial searchnode to the
     * current node in the Tree structure.
     *
     * @param searchNode the search node from witch the path will be build
     */
    public MapColoringPath(SearchNode searchNode) {
        super(searchNode);
    }
}
