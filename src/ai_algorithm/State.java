package ai_algorithm;

import ai_algorithm.problems.Problem;
import ecs.GameObject;

public abstract class State extends GameObject {

	public abstract Problem getProblem();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
}
