package ai_algorithm.problems;

import ecs.GameObject;

public abstract class State extends GameObject {

	
	/**
	 * gibt verweis auf das Problem an
	 * 
	 * @return
	 */
	public abstract Problem getProblem();

	/**
	 * errechnet den hashwert
	 */
	public abstract int hashCode();

	/**
	 * prüft auf gleichwertigkeit
	 */
	public abstract boolean equals(Object obj);
}
