package ecs.visitors;

import java.util.Iterator;
import java.util.List;

import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import application.UpdateRegistry;
import ecs.GameObject;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.TreeLayouter;
import javafx.scene.paint.Color;

public class ComponentUpdateVisitor extends Visitor {

	private static long numUpdates = 0L;

	public void visit(GameObject gameObject) {
		super.visit(gameObject);

		if (gameObject instanceof SearchNode s) {
			this.visit(s);
			return;
		}

		System.out.println("No Maching component");
	}

	public void visit(SearchNode gameObject) {
		super.visit(gameObject);
		numUpdates++;
		System.out.println("NumUpdates: " + numUpdates);
		if (gameObject.metadata.expanding != null) {
			var c = gameObject.metadata.expanding.getComponent(Coloring.class);
			c.setColor(Color.PURPLE);
		}
		if (isInFrontier(gameObject)) {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Color.CYAN);
		} else {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Color.DARKGREY);
		}

		if (gameObject.getState().getProblem().isGoalState(gameObject.getState())) {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Color.RED);
		}

		if (gameObject.getParent() != null) {
			TreeLayouter parentTreeLayouter = gameObject.getParent().getComponent(TreeLayouter.class);
			parentTreeLayouter.layout();
			System.out.println("parent layouted");
		} else {
			gameObject.getComponent(TreeLayouter.class).layout();
		}

	}

	private boolean isInFrontier(SearchNode s) {
		List<Frontier> frontiers = UpdateRegistry.getAllGameObjectsOfType(Frontier.class);
		for (Frontier frontier : frontiers) {
			if (frontier.contains(s)) {
				System.out.println("is in frontier " + s);
				return true;
			}
			System.out.println("FRONITIER >>> " + frontier);
		}
		System.out.println("Not in frontier");

		return false;
	}
}
