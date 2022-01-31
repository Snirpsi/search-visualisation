package ai_algorithm.search;

import java.util.List;

import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import application.debugger.Debugger;

/**
 * Manuelle suche: Knoten der expandiert werden soll selektieren und
 * anschlieﬂend step klicken
 * 
 * @author Severin
 *
 */
public class ManualSearch extends SearchAlgorithm {

	@Override
	public Path search() {
		SearchNode node = new SearchNode(null, this.problem.getInitialState(), 0, null);
		if (problem.isGoalState(node.getState())) {
			return node.getPath();
		}
		SearchNodeMetadataObject.select(node);
		while (SearchNodeMetadataObject.selected != null) {
			Debugger.pause("Select a node to expand:");
			List<SearchNode> children = SearchNodeMetadataObject.selected.expand();
			for (SearchNode c : children) {
				if (problem.isGoalState(c.getState())) {
					return c.getPath();
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
