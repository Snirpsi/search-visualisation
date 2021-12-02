package ai_algorithm;

import java.util.LinkedList;
import java.util.List;

import application.UpdateRegistry;
import ecs.GameObject;

public class SearchNode extends GameObject {

	private final SearchNode parent;

	private LinkedList<SearchNode> children = null;

	private final State state;

	private final double pathCost;

	private final String action;

	private Solution solution;

	public SearchNodeMetadataObject metadata;

	public SearchNode(SearchNode parent, State state, double pathCost, String action) {
		super();
		this.metadata = new SearchNodeMetadataObject();


		this.parent = parent;
		this.state = state;
		this.pathCost = pathCost;
		this.action = action;
		if (this.parent != null) {
			this.metadata.root = this;
			if (this.parent.children == null) {
				this.parent.children = new LinkedList<SearchNode>();
			} else {
				this.parent.children.add(this);
			}
		}
		this.solution = new Solution(this);
		
		this.metadata.expanding = parent;
		this.metadata.expanding = parent;
		this.metadata.expanding = parent;
		
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

	public String getAction() {
		return action;
	}

	public LinkedList<SearchNode> getChildren() {
		return children;
	}

	public void setChildren(LinkedList<SearchNode> children) {
		this.children = children;
		UpdateRegistry.registerForLargeComponentUpdate(this);
	}

	/**
	 * @return the solution
	 */
	public Solution getSolutionActions() {
		return solution;
	}
}
