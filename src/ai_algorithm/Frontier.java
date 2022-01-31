package ai_algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import ai_algorithm.problems.State;
import ecs.GameObject;
import ecs.GameObjectRegistry;

/**
 * Structure for managing the nodes that are still to be expanded
 * 
 * @author Severin
 * 
 */
public class Frontier extends GameObject { // maby it is althou collection ??? {
	/**
	 * administrative structure
	 */
	private List<SearchNode> frontier;

	/**
	 * create new frontier
	 */
	public Frontier() {
		super();
		frontier = new ArrayList<SearchNode>();
	}

	/**
	 * add {@link SearchNode}
	 * 
	 * @param node
	 */
	public void add(SearchNode node) {
		// eventuell notify hinzufügen für fallunterscheidung von verschiedenen aktionen
		this.frontier.add(node);
		node.metadata.isInFrontier = true;
		node.metadata.isInMemory = true;
//visualisieren
		GameObjectRegistry.registerForStateChange(node);
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * add search nodes
	 * 
	 * @param list of nodes
	 */
	public void addAll(List<SearchNode> nodes) {
		frontier.addAll(nodes);
		for (SearchNode node : nodes) {
			node.metadata.isInFrontier = true;
			node.metadata.isInMemory = true;
			GameObjectRegistry.registerForStateChange(node);
		}
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * sorts the search nodes according to a given criterion
	 * 
	 * @param evaluation {@link Function}
	 */
	public void sort(Function<SearchNode, Double> evaluationFunction) {
		frontier.sort(new Comparator<SearchNode>() {
			@Override
			public int compare(SearchNode o1, SearchNode o2) {

				if (evaluationFunction.apply(o1) > evaluationFunction.apply(o2)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * check if it is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	/**
	 * indicates fill level
	 * 
	 * @return size
	 */
	public int size() {
		return frontier.size();
	}

	/**
	 * gives first search node from the Frontier
	 * 
	 * @return
	 */

	public SearchNode removeFirst() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode first = frontier.remove(0);
		first.metadata.isInFrontier = false;
		first.metadata.isInMemory = false;
		return first;
	}

	/**
	 * gives the last search node of the frontier
	 * 
	 * @return
	 */

	public SearchNode removeLast() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode last = frontier.remove(frontier.size() - 1);
		last.metadata.isInFrontier = false;
		last.metadata.isInMemory = false;
		return last;
	}

	/**
checks if a search node is included or not
	 * @param searchnode
	 * @return true, false
	 */

	public boolean contains(SearchNode searchnode) {
		if (frontier.contains(searchnode)) {
			return true;
		}
		return false;
	}

	/**
	 * checks if a search node with corresponding state exists
	 * 
	 * @param state
	 * @return true, false
	 */
	public boolean containsNodeWithState(State state) {
		for (SearchNode searchNode : frontier) {
			if (searchNode.getState().equals(state)) {
				return true;
			}
		}
		return false;
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