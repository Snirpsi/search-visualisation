package ecs.visitors;

import ecs.GameObject;

public abstract class Visitor {

	public void visit(GameObject gameObject) {
		// System.out.println("The object " + gameObject + " was visited by " + this);
	}
}
