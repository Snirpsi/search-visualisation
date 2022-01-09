package ecs.visitors;

import ecs.GameObject;

/**
 * The visitor class is used to visit {@link GameObject} to add or remove or
 * change components of the {@link GameObject}. Each {@link Visitor} that
 * implements this abstract {@link Visitor} should provide a implementation for
 * every possible {@link GameObject} that could be visited.
 * 
 * @author Severin
 *
 */
public abstract class Visitor {

	/**
	 * The behavior of the visitor is defined in the visit method.
	 * 
	 * @param gameObject
	 */
	public void visit(GameObject gameObject) {
		// System.out.println("The object " + gameObject + " was visited by " + this);
	}
}
