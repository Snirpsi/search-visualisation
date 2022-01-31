package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;

/**
 * Tiefensuche aus Nutzerstudie
 *
 * @author Tizian Dippold (tizian.dippold@tum.de)
 *
 */
public class RecursiveDepthSearch extends SearchAlgorithm {

	@Override
	public Path search() {
		SearchNode start = new SearchNode(null, this.problem.getInitialState(), 0, null);
		ExploredSet explored = new ExploredSet();
		return recursiveSearch(start, explored).getPath();
	}

	private SearchNode recursiveSearch(SearchNode s, ExploredSet explored) {
		if (problem.isGoalState(s.getState())) {
			return s;
		}
		explored.add(s);
		for (SearchNode currentChild : s.expand()) {
			if (!explored.contains(currentChild.getState())) {
				SearchNode recursiveErg = recursiveSearch(currentChild, explored);
				if (recursiveErg != null) {
					return recursiveErg;
				}
			}
		}
		return null;
	}

}
/*
 * Copyright (c) 2022 Tizian Dippold
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
