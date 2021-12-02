package ecs.entities.old;

import java.util.List;

import ecs.GameObject;

public class SearchNode extends GameObject {

	public SearchNode parent;
	private List<SearchNode> children = null;
	private State state;
	private double pathCost;
	private Action action;
	private SearchAlgorithm searchAlgorithm;

	public SearchNode() {
		super();
	}

	public SearchNode(SearchNode parent, Action action) {
		this();
		this.parent = parent;
		this.parent.children.add(this);
		this.searchAlgorithm = this.parent.searchAlgorithm;
		this.state = parent.state.getProblem().applyAction(parent.state, action);
		this.action = action;
		this.pathCost = 1.0;
	}

	public SearchNode getParent() {
		return parent;
	}

	public State getState() {
		return state;
	}

	public double getPathCost() {
		return pathCost;
	}

	public Action getAction() {
		return action;
	}
}
