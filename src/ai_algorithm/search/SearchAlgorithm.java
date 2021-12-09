package ai_algorithm.search;

import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;

public abstract class SearchAlgorithm {

	Problem problem;

	public SearchAlgorithm(Problem problem) {
		this.problem = problem;
	}

	public abstract SearchNode search();

}
