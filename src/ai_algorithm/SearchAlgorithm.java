package ai_algorithm;

public abstract class SearchAlgorithm {

	Problem problem;

	public SearchAlgorithm(Problem problem) {
		this.problem = problem;
	}

	public abstract SearchNode search();

}
