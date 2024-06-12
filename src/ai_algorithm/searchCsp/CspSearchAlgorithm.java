package ai_algorithm.searchCsp;

import ai_algorithm.Path;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.search.SearchAlgorithm;

/**
 * Template for csp search algorithm as an extension of SearchAlgorithm
 * 
 * @author Alexander
 */
public abstract class CspSearchAlgorithm extends SearchAlgorithm {
	/**
	 * Problem for the security algorithm is solved by dependency injection
	 */
	CspProblem problem;

	/**
	 * Default Constructor
	 */
	public CspSearchAlgorithm() {

	}

	/**
	 * Create csp search algorithm with problem
	 *
	 * @param problem
	 */
	public CspSearchAlgorithm(CspProblem problem) {
		this.problem = problem;
	}

	/**
	 * Set algorithm problem
	 * 
	 * @param problem
	 */
	public void setProblem(CspProblem problem) {
		this.problem = problem;
	}

	/**
	 * Starts the search
	 * @return Zielpfad
	 */
	public abstract Path search();

}

/*
 * Copyright (c) 2024 Alexander Ultsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
