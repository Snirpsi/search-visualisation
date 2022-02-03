package ai_algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ai_algorithm.problems.State;
import ecs.GameObject;
import ecs.GameObjectRegistry;

/**
 * Data structure for "marking" the already visited states
 * 
 * @author Severin
 *
 */
public class ExploredSet extends GameObject {

	/**
	 * management in hash map
	 */
	HashMap<State, SearchNode> explored;

	/**
	 * Creates new explored set
	 */
	public ExploredSet() {
		super();
		explored = new HashMap<State, SearchNode>();

	}

	/**
	 * Add a node to the explored set the state is automatically extracted
	 * 
	 * @param node
	 */
	public void add(SearchNode node) {
		State state = node.getState();
		this.explored.put(state, node);
		node.metadata.isInExploredSet = true;
		node.metadata.isInMemory = true;
		// inform visualization about change
		GameObjectRegistry.registerForStateChange(node);
		GameObjectRegistry.registerForStateChange(state);
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * Add multiple nodes to the explored set the state is automatically extracted
	 * 
	 * @param node
	 */
	public void addAll(List<SearchNode> nodes) {
		for (SearchNode node : nodes) {
			explored.put(node.getState(), node);
			node.metadata.isInExploredSet = true;
			node.metadata.isInMemory = true;
			// inform visualization about change
			GameObjectRegistry.registerForStateChange(node.getState());
			GameObjectRegistry.registerForStateChange(node);
		}
		// inform visualization about change
		GameObjectRegistry.registerForStateChange(this);
	}

	/**
	 * Check if values are present
	 * 
	 * @return true oder false
	 */
	public boolean isEmpty() {
		return explored.isEmpty();
	}

	/**
	 * indicates if a state is contained in the explored set
	 * 
	 * @param state
	 * @return ture, false
	 */
	public boolean contains(State state) {
		if (explored.containsKey(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the last inserted node which corresponds to the state
	 * 
	 * @param state
	 * @return Node identified via state
	 */
	public SearchNode get(State state) {
		return explored.get(state);
	}

	/**
	 * deletes the entire content of the {@link ExploredSet} set
	 */
	public void clear() {
		explored.clear();
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