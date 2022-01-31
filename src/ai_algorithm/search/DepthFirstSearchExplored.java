package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.State;
import application.debugger.Debugger;

/**
 * Tiefensuche mit Explored-Set
 * 
 * @author Severin
 *
 */
public class DepthFirstSearchExplored extends SearchAlgorithm {
	@Override
	public Path search() {
		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		Frontier frontier = new Frontier();
		ExploredSet explored = new ExploredSet();
		explored.add(start);
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
			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					Debugger.pause("Finished");
					return child.getPath();
				}
				if (!explored.contains(state)) {
					Debugger.pause();
					explored.add(child);
					frontier.add(child);
				}
			}
		}
		return null;
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
