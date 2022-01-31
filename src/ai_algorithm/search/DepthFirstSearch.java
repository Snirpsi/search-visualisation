package ai_algorithm.search;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import application.debugger.Debugger;
/**
 * Tiefensuche nach Mittag
 * 
 * @author Severin
 *
 */
public class DepthFirstSearch extends SearchAlgorithm {
//default construchtor required

	public DepthFirstSearch() {
		super();
	}

	public DepthFirstSearch(Problem problem) {
		super(problem);
	}

	@Override
	public Path search() {
		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		Frontier frontier = new Frontier();
		Debugger.pause();
		if (this.problem.isGoalState(start.getState())) {
			return start.getPath();
		}
		frontier.add(start);
		Debugger.pause();
		while (!frontier.isEmpty()) {
			SearchNode node = frontier.removeLast();
			Debugger.pause();
			System.out.println(node);
			if (problem.isGoalState(node.getState())) {
				return node.getPath();
			}
			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					Debugger.pause("Finished");
					return child.getPath();
				}
				if (!contains2(node, state)) {
					frontier.add(child);
				}
			}
		}
		Debugger.pause("No Sulution found");
		return null;
	}

	public boolean contains(SearchNode sn, State state) {
		if (sn.getState().equals(state)) {
			return true;
		} else if (sn.getParent() != null) {
			return contains(sn.getParent(), state);
		}
		return false;
	}

	public boolean contains2(SearchNode sn, State state) {
		return sn.getPath().getVisitedStates().contains(state);
	}

}
/*
 * Copyright (c) 2022 Severin Dippold
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
