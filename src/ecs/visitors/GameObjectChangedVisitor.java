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

/**
 * This class handles the change of every possible {@link GameObject}.
 * 
 * @author Severin
 *
 */

public class GameObjectChangedVisitor extends Visitor {

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

		System.out.println("NumUpdates: " + numUpdates);

		// not in memory
		SearchNode parr = searchNode;
		while (parr.getParent() != null) {
			if (parr.metadata.isInExploredSet || parr.metadata.isInFrontier) {
				parr.metadata.isInMemory = true;
			} else {
				parr.metadata.isInMemory = false;
			}
			parr = parr.getParent();
		}
		// is in memory
		parr = SearchNodeMetadataObject.expanding;

		while (parr != null && parr.getParent() != null) {
			parr.metadata.isInMemory = true;
			parr = parr.getParent();
		}

		var c = searchNode.getComponent(Coloring.class);
		// set node collor
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

		GameObjectRegistry.registerForStateChange(searchNode.getState().getProblem());
	}

	public void visit(RasterPathProblem rasterPathProblem) {
		// Makieren von Frontier und Explored set in dem spezifischen Problem,
		// ist Problemabhängig ob es geht oder nicht.
		List<SearchNode> nodes = GameObjectRegistry.getAllGameObjectsOfType(SearchNode.class);
		TileMap2D t2d = rasterPathProblem.getComponent(TileMap2D.class);
		for (SearchNode node : nodes) { // <<- is ja nur O(n) -.-
			RasterPathState state;
			try {
				state = (RasterPathState) node.getState();
			} catch (Exception e) {
				System.out.println("state is not of type Rasterpath");
				return;
			}

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
