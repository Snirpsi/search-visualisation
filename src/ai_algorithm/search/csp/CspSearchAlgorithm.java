package ai_algorithm.search.csp;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.CspState;
import ai_algorithm.problems.State;
import ai_algorithm.search.SearchAlgorithm;
import application.debugger.Debugger;
import ecs.GameObjectRegistry;

import java.util.List;

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

	/**
	 * Expand the search node with actions for the given variable
	 * @return list of search nodes
	 */
	public List<SearchNode> expand(SearchNode searchNode, Frontier frontier, String variable, List<String> values) {
		// Start visualization
		SearchNodeMetadataObject.setExpandingSearchnode(searchNode);
		Debugger.pause("Expanding: " + this);
		// End visualization

		if (searchNode.getChildren() != null && !searchNode.getChildren().isEmpty() ) {
			return searchNode.getChildren(); // <-- only expand children if they don't already exist
		}

		CspState state = (CspState) searchNode.getState();
		CspProblem prob = (CspProblem)state.getProblem();

		for (String value : values) {
			String action = variable + "=" + value;
			State succState = prob.getSuccessor(state, action);
			// Create new SearchNode
			SearchNode succ = new SearchNode(searchNode, succState,
					searchNode.getPathCost() + prob.getCost(state, action, succState), action);
			searchNode.getChildren().add(succ);
			frontier.add(succ);
			Debugger.pause("EXPANSION: " + action);
		}

		// Start visualization
		GameObjectRegistry.registerForStateChange(searchNode);
		// End visualization
		return searchNode.getChildren();
	}
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
