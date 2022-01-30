package ai_algorithm.problems.raster_path;

import ai_algorithm.SearchNode;
import ai_algorithm.Path;

public class GridMazePath extends Path {

	/**
	 * The Constructor is used to get the Path from the initial searchnode to the
	 * currend node in the Tree structure.
	 * 
	 * @param searchNode the searchnode from witch the path will be build
	 * @since version 1.1
	 */
	public GridMazePath(SearchNode searchNode) {
		super(searchNode);
	}

}
