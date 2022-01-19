package ai_algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ecs.GameObject;
import ecs.GameObjectRegistry;

public class ExploredSet extends GameObject {

	HashMap<State, SearchNode> explored;

	public ExploredSet() {
		super();
		explored = new HashMap<State, SearchNode>();

	}

	public void add(SearchNode node) {
		State state = node.getState();
		this.explored.put(state, node);
		node.metadata.isInExploredSet = true;
		node.metadata.isInMemory = true;
		GameObjectRegistry.registerForStateChange(node);
		GameObjectRegistry.registerForStateChange(state);
		GameObjectRegistry.registerForStateChange(this);
	}

	public void addAll(List<SearchNode> nodes) {

		for (SearchNode node : nodes) {
			explored.put(node.getState(), node);
			node.metadata.isInExploredSet = true;
			node.metadata.isInMemory = true;
			GameObjectRegistry.registerForStateChange(node.getState());
			GameObjectRegistry.registerForStateChange(node);
		}
		GameObjectRegistry.registerForStateChange(this);
	}

	public boolean isEmpty() {
		return explored.isEmpty();
	}

	public boolean contains(State state) {
		if (explored.containsKey(state)) {
			return true;
		}
		return false;
	}

	public SearchNode get(State state) {
		return explored.get(state);
	}

	public void clear() {
		explored.clear();
	}

}
