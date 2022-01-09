package ecs;

/**
 * This abstract class is a Visitor that can be applied to the
 * {@link GameObject}.
 * 
 * @author Severin
 *
 */

public abstract class GameObjectVisitor {
	/**
	 * This Function visits the given {@link GameObject}.
	 * 
	 * @param gameObject The {@link GameObject} to visit.
	 */
	public abstract void visit(GameObject gameObject);
}
