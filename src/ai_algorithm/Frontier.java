package ai_algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import ecs.GameObject;
import ecs.GameObjectRegistry;

public class Frontier extends GameObject { // maby it is althou collection ??? {

	private List<SearchNode> frontier;

	public Frontier() {
		super();
		frontier = new ArrayList<SearchNode>();
	}

	public void add(SearchNode node) {
		// eventuell notify hinzufügen für fallunterscheidung von verschiedenen aktionen
		this.frontier.add(node);
		node.metadata.isInFrontier = true;
		GameObjectRegistry.registerForLargeComponentUpdate(node);
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	public void addAll(List<SearchNode> nodes) {
		frontier.addAll(nodes);
		for (SearchNode node : nodes) {
			node.metadata.isInFrontier = true;
			GameObjectRegistry.registerForLargeComponentUpdate(node);
		}
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

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
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	public SearchNode removeFirst() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode first = frontier.remove(0);
		first.metadata.isInFrontier = false;
		return first;
	}

	public SearchNode removeLast() {
		if (frontier.isEmpty()) {
			return null;
		}
		SearchNode last = frontier.remove(frontier.size()-1);
		last.metadata.isInFrontier = false;
		return last;
	}

	public boolean contains(SearchNode searchnode) {
		if (frontier.contains(searchnode)) {
			return true;
		}
		return false;
	}

	public boolean containsNodeWithState(State state) {
		for (SearchNode searchNode : frontier) {
			if (searchNode.getState().equals(state)) {
				return true;
			}
		}
		return false;
	}

}
