package ai_algorithm;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import application.debugger.Debugger;
import ecs.GameObject;
import ecs.GameObjectRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Search nodes are the central data structure of the AI algorithms, they make
 * up the tree
 * 
 * @author Severin
 */
public class SearchNode extends GameObject {

	/**
	 * parent node
	 */
	private final SearchNode parent;
	/**
	 * child node
	 */
	private final List<SearchNode> children;
	/**
	 * state
	 */
	private final State state;
	/**
	 * path costs
	 */
	private final double pathCost;
	/**
	 * action
	 */
	private final String action;
	/**
	 * reference to the path traveled
	 */
	private final Path path;

	/**
	 * object mainly for visualization and Thread communication
	 */
	public SearchNodeMetadataObject metadata;

	/**
	 * Constructor
	 * 
	 * @param parent   null if root node
	 * @param state    when root start condition
	 * @param pathCost path costs
	 * @param action   action that led to condition
	 */
	public SearchNode(SearchNode parent, State state, double pathCost, String action) {
		super();
		this.metadata = new SearchNodeMetadataObject();
		this.children = new LinkedList<>();
		GameObjectRegistry.registerForStateChange(this);

		this.parent = parent;
		this.state = state;
		this.pathCost = pathCost;
		this.action = action;
		if (this.parent == null) {
			this.metadata.root = this;
		}
		this.path = new Path(this);

	}

	/**
	 * returns parent node
	 * 
	 * @return returns parent node
	 */
	public SearchNode getParent() {
		return parent;
	}

	/**
	 * tests if node is root
	 * 
	 * @return true, false
	 */
	public boolean isRoot() {
        return this.getParent() == null;
    }

	/**
	 * gives associated state
	 * 
	 * @return state
	 */
	public State getState() {
		return state;
	}

	/**
	 * returns
	 * 
	 * @return the path cost
	 */
	public double getPathCost() {
		return pathCost;
	}

	/**
	 * action that led to condition
	 * 
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * specifies all child nodes as a list
	 * 
	 * @return list of all child nodes
	 */
	public List<SearchNode> getChildren() {
		return children;
	}

//	/**
//	 * sets the child nodes
//	 *
//	 * @param children
//	 */
//	public void setChildren(LinkedList<SearchNode> children) {
//		this.children = children;
//		GameObjectRegistry.registerForStateChange(this);
//	}

	/**
	 * @return path object
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * automatically applies all actions possible in the state of the node and
	 * inserts them into new nodes. all nodes are then added to the expanded node as
	 * children.
	 * 
	 * it is recommended to use this method and call it at least once per iteration
	 * 
	 * @return List of expanded child nodes
	 */
	public List<SearchNode> expand() {
		// visualisierung
		SearchNodeMetadataObject.setExpandingSearchnode(this);
		Debugger.pause("Expanding: " + this);
		// ende vis

		if (this.getChildren() != null && !this.getChildren().isEmpty() ) {
			return this.getChildren(); // <-- only expand children if they don't alredy exist
		}

		State state = this.getState();
		Problem prob = state.getProblem();
		for (String action : prob.getActions(state)) {
			State succState = prob.getSuccessor(state, action);
			// neue knoten erzeugen
			SearchNode succ = new SearchNode(this, succState,
					this.getPathCost() + prob.getCost(state, action, succState), action);
			this.children.add(succ);
			Debugger.pause("EXPANSION: " + action);
		}
		// visualsiieren
		GameObjectRegistry.registerForStateChange(this);
		// ende vis
		return this.children;
	}

	@Override
	public String toString() {
		return "SearchNode [state=" + state + ", pathCost=" + pathCost + ", action=" + action + "]";
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
