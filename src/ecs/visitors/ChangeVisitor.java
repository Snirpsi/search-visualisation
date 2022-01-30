package ecs.visitors;

import java.util.List;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.raster_path.GridMazeProblem;
import ai_algorithm.problems.raster_path.GridMazeState;
import application.Globals;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import ecs.components.InputHandler;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.Graphics;
import ecs.components.graphics.TreeLayouter;
import ecs.components.graphics.drawables.Sprite;
import ecs.components.graphics.drawables.Text;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.shape.Shape;
import settings.Settings;
import tools.Vector2DInt;

/**
 * This class handles the change of every possible {@link GameObject}.
 * 
 * @author Severin
 *
 */

public class ChangeVisitor extends Visitor {

	private static long numUpdates = 0L;

	public void visit(GameObject gameObject) {
		super.visit(gameObject);

		if (gameObject instanceof SearchNode s) {
			this.visit(s);
			return;
		}
		if (gameObject instanceof GridMazeProblem p) {
			this.visit(p);
			return;
		}

		if (gameObject instanceof Frontier frontier) {
			this.visit(frontier);
			return;
		}

		// System.out.println("No Maching component");
	}

	// Frontier ändern
	public void visit(Frontier frontier) {
		frontier.getComponent(Text.class).setText("Frontier: " + frontier.size());
	}

	public void visit(SearchNode searchNode) {
		super.visit(searchNode);
		numUpdates++;

		// System.out.println("NumUpdates: " + numUpdates);

		// not in memory setzen
		if (searchNode == SearchNodeMetadataObject.prevExpanding) {
			SearchNode prevExp = SearchNodeMetadataObject.prevExpanding;

			while (prevExp != null && prevExp.getParent() != null) {
				if (prevExp.metadata.isInExploredSet || prevExp.metadata.isInFrontier) {
					prevExp.metadata.isInMemory = true;
				} else {
					prevExp.metadata.isInMemory = false;
				}
				setCollorAccordingToState(prevExp);
				prevExp = prevExp.getParent();

			}
		}
		// is in memory setzen
		SearchNode exp = SearchNodeMetadataObject.expanding;

		while (exp != null && exp.getParent() != null) {
			exp.metadata.isInMemory = true;
			setCollorAccordingToState(exp);
			exp = exp.getParent();

		}

		setCollorAccordingToState(searchNode);

		// problem aktualisieren
		GameObjectRegistry.registerForStateChange(searchNode.getState().getProblem());

		// selektieren
		if (searchNode == SearchNodeMetadataObject.selected) {
			Sprite s = searchNode.getComponent(Sprite.class);
			s.getShapes().forEach(gNode -> {
				if (gNode instanceof Shape shape) {
					shape.setStyle(" -fx-stroke: pink; -fx-stroke-width: " + 10 + ";");
				}
			});

			SearchNode desel = SearchNodeMetadataObject.deselected;
			if (desel != null && desel != SearchNodeMetadataObject.selected) {
				s = desel.getComponent(Sprite.class);
				s.getShapes().forEach(gNode -> {
					if (gNode instanceof Shape shape) {
						shape.setStyle("-fx-stroke-width: " + 0 + ";");
					}
				});
			}
		}

		// aktiven zustand zeichnen
		Globals.stateRepresentationGraphicsContext.getChildren().clear();
		searchNode.getState().getProblem().getComponent(Graphics.class).show();
		searchNode.getState().getComponent(Graphics.class).show();
		searchNode.getPath().getComponent(Graphics.class).show();
		searchNode.getComponent(TreeLayouter.class).layout();
	}

	private void setCollorAccordingToState(SearchNode searchNode) {
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
			c.setColor(Settings.DEFAULTCOLORS.EXPANDED);
		} else if (searchNode.metadata.isInExploredSet) {
			c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY);
		} else if (searchNode.metadata.isInMemory) {
			c.setColor(Settings.DEFAULTCOLORS.IN_MEMORY);
		} else {
			c.setColor(Settings.DEFAULTCOLORS.NOT_IN_MEMORY);
		}
	}

	public void visit(GridMazeProblem rasterPathProblem) {
		// Makieren von Frontier und Explored set in dem spezifischen Problem,
		// ist Problemabhängig ob es geht oder nicht.
		List<SearchNode> nodes = GameObjectRegistry.getAllGameObjectsOfType(SearchNode.class);
		TileMap2D t2d = rasterPathProblem.getComponent(TileMap2D.class);
		for (SearchNode node : nodes) { // <<- is ja nur O(n) -.- <besser währe alle frontiers und exploredsets zu
										// bekommen
			GridMazeState state;
			try {
				state = (GridMazeState) node.getState();
			} catch (Exception e) {
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

}
