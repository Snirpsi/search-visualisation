package ecs.entities.old;

import java.util.List;

import ecs.GameObject;

public abstract class Problem extends GameObject {

	public abstract State getInitialState();

	public abstract boolean isGoalState(State state);

	public abstract List<Action> getActions(State state);

	public abstract State applyAction(State state, Action action);

	public abstract double getCost(State state, String action);
}
