package ecs.entities.old;

import ecs.GameObject;

public abstract class SearchAlgorithm extends GameObject {
	// Function<State, Double> evaluationFunction = null;
	public SearchAlgorithm() {
	}

	public abstract void search(); // TODO: search algorithm
}
