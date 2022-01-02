package ai_algorithm;

import java.util.HashSet;
import java.util.List;

import ecs.GameObject;
import ecs.GameObjectRegistry;

public class ExploredSet extends GameObject {

	HashSet<State> explored;

	public ExploredSet() {
		super();
		explored = new HashSet<State>();

	}

	public void add(SearchNode node) {
		State state = node.getState();
		this.explored.add(state);
		node.metadata.isInExploredSet = true;
		GameObjectRegistry.registerForLargeComponentUpdate(state);
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	public void addAll(List<SearchNode> nodes) {

		for (SearchNode node : nodes) {
			explored.add(node.getState());
			node.metadata.isInExploredSet = true;
			GameObjectRegistry.registerForLargeComponentUpdate(node.getState());
		}
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	public boolean isEmpty() {
		return explored.isEmpty();
	}

	public boolean contains(State state) {
		if (explored.contains(state)) {
			return true;
		}
		return false;
	}

	public void clear() {
		explored.clear();
	}

}
