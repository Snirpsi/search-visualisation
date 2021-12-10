package ai_algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import application.Debugger;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import javafx.scene.Node;

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
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	/**
	 * @return the solution
	 */
	public Solution getSolutionActions() {
		return solution;
	}

	public List<SearchNode> expand() {
		List<SearchNode> futureCildren = new ArrayList<>();
		
		if( this.getChildren() != null && this.getChildren().size() != 0) {
			return this.getChildren(); // <-- only expand children if they don't alredy exist
		}

		State state = this.getState();
		Problem prob = state.getProblem();
		for (String action : prob.getActions(state)) {
			Debugger.pause();

			State succState = prob.getSuccessor(state, action);
			SearchNode succ = new SearchNode(this, succState,
					this.getPathCost() + prob.getCost(state, action, succState), action);
			futureCildren.add(succ);
		}
		
		this.children.addAll(futureCildren);

		return this.children;
	}
}
