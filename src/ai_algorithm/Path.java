package ai_algorithm;

import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import ecs.GameObject;

public class Path extends GameObject {
	SearchNode searchNode;

	/**
	 * The Constructor is used to get the Path from the initial searchnode to the
	 * currend node in the Tree structure.
	 * 
	 * @param searchNode the searchnode from witch the path will be build
	 */
	public Path(SearchNode searchNode) {
		this.searchNode = searchNode;
	}

	/**
	 * Returns the problem the Path is Corresponding to.
	 */
	public Problem getProblem() {
		return this.searchNode.getState().getProblem();
	}

	/**
	 * gibt die aktionen die auf einem pfad liegen aus
	 * 
	 * @return
	 */

	public List<String> getPathActions() {
		List<String> actionList = new LinkedList<String>();
		SearchNode next = this.searchNode;
		while (next != null) {
			actionList.add(actionList.size(), next.getAction());
			next = next.getParent();
		}
		return actionList;
	}

	/**
	 * gibt die zustände aus die auf einem pfad liegen
	 * 
	 * @return
	 */
	public List<State> getVisitedStates() {
		List<State> stateList = new LinkedList<State>();
		SearchNode next = this.searchNode;
		while (next != null) {
			stateList.add(stateList.size(), next.getState());
			next = next.getParent();
		}
		return stateList;
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