package ai_algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import application.debugger.Debugger;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import javafx.scene.Node;

public class SearchNode extends GameObject {

	private final SearchNode parent;

	private LinkedList<SearchNode> children = null;

	private final State state;

	private final double pathCost;

	private final String action;

	private Path solution;

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
		this.solution = new Path(this);

	}

	public SearchNode getParent() {
		return parent;
	}

	public boolean isRoot() {
		if (this.getParent() == null) {
			return true;
		}
		return false;
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
	public Path getPath() {
		return solution;
	}

	public List<SearchNode> expand() {
		this.metadata.expanding = this;// <-- marker auf die expandierenden knoten setzen
		GameObjectRegistry.registerForLargeComponentUpdate(this);
		Debugger.pause("Expanding: " + parent);
		List<SearchNode> futureCildren = new ArrayList<>();

		if (this.getChildren() != null && this.getChildren().size() != 0) {
			return this.getChildren(); // <-- only expand children if they don't alredy exist
		}

		State state = this.getState();
		Problem prob = state.getProblem();
		for (String action : prob.getActions(state)) {
			State succState = prob.getSuccessor(state, action);
			SearchNode succ = new SearchNode(this, succState,
					this.getPathCost() + prob.getCost(state, action, succState), action);
			futureCildren.add(succ);
			Debugger.pause("EXPANSION: " + action);
		}
		this.children.addAll(futureCildren);
		GameObjectRegistry.registerForLargeComponentUpdate(this);
		return this.children;
	}
	
	 public boolean contains(State state) {
	        if (this.state.equals(state)) {
	            return true;
	        } else if (parent != null) {
	            return parent.contains(state);
	        }
	        return false;
	    }


}
