package ai_algorithm.search;

import ai_algorithm.Problem;
import ai_algorithm.SearchNode;

public abstract class SearchAlgorithm {

	Problem problem;

	public SearchAlgorithm(Problem problem) {
		this.problem = problem;
	}

	public abstract SearchNode search();

}
