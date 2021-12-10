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

	public void add(State state) {
		this.explored.add(state);
		GameObjectRegistry.registerForLargeComponentUpdate(state);
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}

	public void addAll(List<State> states) {
		explored.addAll(states);
		for (State state : states) {
			GameObjectRegistry.registerForLargeComponentUpdate(state);
		}
		GameObjectRegistry.registerForLargeComponentUpdate(this);
	}
	
	public boolean isEmpty () {
		return explored.isEmpty();
	}
	
	public boolean contains (State state) {
		if(explored.contains(state)) {
			return true;
		}
		return false;
	}
	
	public  void clear() {
		explored.clear();
	}

}
