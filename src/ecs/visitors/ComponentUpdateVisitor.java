package ecs.visitors;

import java.util.List;

import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.problems.raster_path.RasterPathState;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import ecs.components.InputHandler;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.drawables.TileMap2D;
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

	public void visit(SearchNode searchNode) {
		super.visit(searchNode);
		numUpdates++;

		if (SearchNodeMetadataObject.expanding != null
				&& searchNode.metadata.expanding.hasComponent(InputHandler.class)) {
			var ih = searchNode.metadata.expanding.getComponent(InputHandler.class);
			ih.handle(null);
		}

		System.out.println("NumUpdates: " + numUpdates);

		var c = searchNode.getComponent(Coloring.class);

		if (searchNode.getState().getProblem().isGoalState(searchNode.getState())) {
			c.setColor(Settings.DEFAULTCOLORS.GOAL);
		} else if (SearchNodeMetadataObject.expanding == searchNode) {
			var col = SearchNodeMetadataObject.expanding.getComponent(Coloring.class);
			col.setColor(Settings.DEFAULTCOLORS.EXPANDING);
		} else if (searchNode.metadata.isInFrontier) {
			c.setColor(Settings.DEFAULTCOLORS.IN_FRONTIER);
		} else if (searchNode.getChildren() != null && searchNode.metadata.isInExploredSet) {
			System.out.println("EXPANDED COLORING !!!!!!!!!!!!!!!!!!!!!");
			c.setColor(Settings.DEFAULTCOLORS.EXPANDED);
		} else if (searchNode.metadata.isInExploredSet) {
			c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY);
		} else if (searchNode.metadata.isInMemory) {
			c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY);
		} else {
			c.setColor(Settings.DEFAULTCOLORS.NOT_IN_MEMORY);
		}

		/*
		 * if (searchNode.metadata.expanding == searchNode) { var c =
		 * searchNode.metadata.expanding.getComponent(Coloring.class);
		 * c.setColor(Settings.DEFAULTCOLORS.EXPANDING); } if (isInFrontier(searchNode))
		 * { var c = searchNode.getComponent(Coloring.class);
		 * c.setColor(Settings.DEFAULTCOLORS.IN_FRONTIER); } else { var c =
		 * searchNode.getComponent(Coloring.class);
		 * c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY); }
		 * 
		 * if (searchNode.getState().getProblem().isGoalState(searchNode.getState())) {
		 * var c = searchNode.getComponent(Coloring.class);
		 * c.setColor(Settings.DEFAULTCOLORS.GOAL); }
		 * 
		 * if (searchNode.getChildren() != null ) { var c =
		 * searchNode.getComponent(Coloring.class);
		 * c.setColor(Settings.DEFAULTCOLORS.EXPANDED); }
		 * 
		 * if (searchNode.getParent() != null) { TreeLayouter parentTreeLayouter =
		 * searchNode.getParent().getComponent(TreeLayouter.class);
		 * parentTreeLayouter.layout(); System.out.println("parent layouted"); } else {
		 * searchNode.getComponent(TreeLayouter.class).layout(); }
		 */

		GameObjectRegistry.registerForLargeComponentUpdate(searchNode.getState().getProblem());
	}

	public void visit(RasterPathProblem rasterPathProblem) {
		// Makieren von Frontier und Explored set in dem spezifischen Problem,
		// ist Problemabhängig ob es geht oder nicht.
		List<SearchNode> nodes = GameObjectRegistry.getAllGameObjectsOfType(SearchNode.class);
		TileMap2D t2d = rasterPathProblem.getComponent(TileMap2D.class);
		for (SearchNode node : nodes) { // <<- is ja nur O(n) -.-
			RasterPathState state = (RasterPathState) node.getState();
			if (t2d.getTile(state.getPosition()) != null) {
				Vector2DInt statePos = state.getPosition();
				if (rasterPathProblem.labyrinth[statePos.x][statePos.y] == 'e') {
					if (node == node.metadata.expanding) {
						// Makiere knoten der Expandiert wird
						t2d.setTileColor(statePos, Settings.DEFAULTCOLORS.EXPANDING);
					} else if (node.metadata.isInFrontier) {
						// Makiere knoten der in frontier ist wird
						t2d.setTileColor(statePos, Settings.DEFAULTCOLORS.IN_FRONTIER);
					} else if (node.metadata.isInExploredSet) {
						// Makiere Zustand in ExploredSet
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
