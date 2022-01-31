package ecs.visitors;

import java.util.List;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.State;
import ai_algorithm.problems.raster_path.GridMazeProblem;
import ai_algorithm.problems.raster_path.GridMazeState;
import ai_algorithm.problems.slidingTilePuzzle.SlidingTileState;
import ai_algorithm.problems.slidingTilePuzzle.SlidingTileTile;
import application.Globals;
import ecs.Component;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import ecs.components.InputHandler;
import ecs.components.Position;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.Graphics;
import ecs.components.graphics.TreeLayouter;
import ecs.components.graphics.drawables.Sprite;
import ecs.components.graphics.drawables.Text;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.shape.Shape;
import settings.Settings;
import tools.Vector2D;
import tools.Vector2DInt;

/**
 * This class handles the change of every possible {@link GameObject}. The
 * ChangeVisitor takes the information largely from the {@link GameObject}s and
 * then tries to apply it to the {@link Component}s. For each GeameObject-Type
 * an fuction must bee implemented that is specific to the type. Thus the
 * visualization is adapted to the changes of the game object.
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

	/**
	 * changes text on {@link Frontier}
	 * 
	 * @param frontier
	 */
	public void visit(Frontier frontier) {
		frontier.getComponent(Text.class).setText("Frontier: " + frontier.size());
	}

	/**
	 * Changes color of {@link SearchNode}
	 * 
	 * @param searchNode
	 */
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
		//Globals.stateRepresentationGraphicsContext.getChildren().clear();
		GameObjectRegistry.registerForStateChange(searchNode.getState());

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

	/**
	 * Visualizes The grid maze problem and applies colors to it if corresponding
	 * {@link State} are in {@link Frontier} and {@link ExploredSet}
	 * 
	 * @param rasterPathProblem
	 */
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

	/**
	 * 
	 * @param slidingTileState
	 */
	public void visit(SlidingTileState slidingTileState) {
		int maxval = slidingTileState.getSize().y * slidingTileState.getSize().x;
		for (int y = 0; y < slidingTileState.getSize().y; y++) {
			for (int x = 0; x < slidingTileState.getSize().x; x++) {
				if (slidingTileState.getField()[y][x].getNum() == 0) {
					continue;
				}
				slidingTileState.getField()[y][x].setPos(x, y);
				slidingTileState.getField()[y][x].getComponent(Graphics.class).show();

			}
		}
	}

	public void visit(SlidingTileTile slider) {
		slider.getComponent(Graphics.class).show();
	}

}
/*
 * Copyright (c) 2022 Severin Dippold
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
