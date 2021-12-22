package ecs.visitors;

import java.util.Iterator;
import java.util.List;

import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.State;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.problems.raster_path.RasterPathState;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.TreeLayouter;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.paint.Color;
import settings.Settings;
import tools.Vector2DInt;

public class ComponentUpdateVisitor extends Visitor {

	private static long numUpdates = 0L;

	public void visit(GameObject gameObject) {
		super.visit(gameObject);

		if (gameObject instanceof SearchNode s) {
			this.visit(s);
			return;
		}
		if (gameObject instanceof RasterPathProblem p) {
			this.visit(p);
			return;
		}

		System.out.println("No Maching component");
	}

	public void visit(SearchNode gameObject) {
		super.visit(gameObject);
		numUpdates++;
		System.out.println("NumUpdates: " + numUpdates);
		if (gameObject.metadata.expanding == gameObject) {
			var c = gameObject.metadata.expanding.getComponent(Coloring.class);
			c.setColor(Settings.DEFAULTCOLORS.EXPANDED);
		}
		if (isInFrontier(gameObject)) {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Settings.DEFAULTCOLORS.IN_FRONTIER);
		} else {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY);
		}

		if (gameObject.getState().getProblem().isGoalState(gameObject.getState())) {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Settings.DEFAULTCOLORS.GOAL);
		}

		if (gameObject == gameObject.metadata.expanding) {
			var c = gameObject.getComponent(Coloring.class);
			c.setColor(Settings.DEFAULTCOLORS.EXPANDING);
		}

		if (gameObject.getParent() != null) {
			TreeLayouter parentTreeLayouter = gameObject.getParent().getComponent(TreeLayouter.class);
			parentTreeLayouter.layout();
			System.out.println("parent layouted");
		} else {
			gameObject.getComponent(TreeLayouter.class).layout();
		}

		GameObjectRegistry.registerForLargeComponentUpdate(gameObject.getState().getProblem());
	}

	public void visit(RasterPathProblem rasterPathProblem) {
		//Makieren von Frontier und Explored set in dem spezifischen Problem,
		// ist Problemabhängig ob es geht oder nicht.
		List<SearchNode> nodes = GameObjectRegistry.getAllGameObjectsOfType(SearchNode.class);
		TileMap2D t2d = rasterPathProblem.getComponent(TileMap2D.class);
		for (SearchNode node : nodes) { // <<- is ja nur O(n) -.-
			RasterPathState state = (RasterPathState) node.getState();
			if (t2d.getTile(state.getPosition()) != null) {
				Vector2DInt statePos = state.getPosition();
				if (rasterPathProblem.labyrinth[statePos.x][statePos.y] == 'e') {
					if (node == node.metadata.expanding) {
						//Makiere knoten der Expandiert wird
						t2d.setTileColor(statePos, Settings.DEFAULTCOLORS.EXPANDING);
					} else if (node.metadata.isInFrontier) {
						//Makiere knoten der in frontier ist wird
						t2d.setTileColor(statePos, Settings.DEFAULTCOLORS.IN_FRONTIER);
					} else if (node.metadata.isInExploredSet) {
						//Makiere Zustand in ExploredSet
						t2d.setTileColor(statePos, Settings.DEFAULTCOLORS.EXPANDED);
					}
				}
			}
		}
	}

	private boolean isInFrontier(SearchNode s) {
		List<Frontier> frontiers = GameObjectRegistry.getAllGameObjectsOfType(Frontier.class);
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
