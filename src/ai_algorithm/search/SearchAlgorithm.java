package ai_algorithm.search;

import ai_algorithm.Path;
import ai_algorithm.problems.Problem;

/**
 * Schablone für suchalgorithmus
 * 
 * @author Severin
 *
 */
public abstract class SearchAlgorithm {
	/**
	 * Problem for the security algorithm is solved by dependency injection
	 */
	Problem problem;

	/**
	 * Default Constructor
	 */
	public SearchAlgorithm() {

	}

	/**
	 * Create search algorithm with problem
	 * 
	 * @param problem
	 */
	public SearchAlgorithm(Problem problem) {
		this.problem = problem;
	}

	/**
	 * Set algorithm problem
	 * 
	 * @param problem
	 */
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	/**
	 * Starts the search
	 * @return Zielpfad
	 */
	public abstract Path search();

}
