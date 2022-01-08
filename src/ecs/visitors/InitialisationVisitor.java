package ecs.visitors;

import java.util.List;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.Path;
import ai_algorithm.State;
import ai_algorithm.problems.raster_path.RasterPathProblem;
import ai_algorithm.problems.raster_path.RasterPathState;
import application.Globals;
import ecs.Component;
import ecs.GameObject;
import ecs.GameObjectRegistry;
import ecs.components.Animation;
import ecs.components.Association;
import ecs.components.InputConnector;
import ecs.components.InputHandler;
import ecs.components.Position;
import ecs.components.TreeComponent;
import ecs.components.graphics.Coloring;
import ecs.components.graphics.Graphics;
import ecs.components.graphics.TreeLayouter;
import ecs.components.graphics.drawables.ConnectionLine;
import ecs.components.graphics.drawables.Sprite;
import ecs.components.graphics.drawables.Text;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import tools.Vector2D;
import tools.Vector2DInt;

public class InitialisationVisitor extends Visitor {
	// default
	public void visit(GameObject gameObject) {
		super.visit(gameObject);
		// switch expression mit Pattern matching
		// wie kann das verhindert werden ?
		if (gameObject instanceof SearchNode p) {
			this.visit(p);
			return;
		}
		if (gameObject.getClass().isAssignableFrom(RasterPathState.class)) {
			this.visit((RasterPathState) gameObject);
			return;
		}
		if (gameObject.getClass().isAssignableFrom(RasterPathProblem.class)) {
			this.visit((RasterPathProblem) gameObject);
			return;
		}
		// Hmmmm..... ka ob das so gut ist ... O.o
		if (gameObject instanceof Path s) {
			if (s.getProblem()instanceof RasterPathProblem prob) {
				this.visit(s, prob);
				return;
			}
		}

		System.out.println("defaultVisitor");
		// Platzhalter zum prüfen
		Component position = new Position(Vector2D.ZERO);
		gameObject.addComponent(position);

		Circle circle = new Circle(0, 0, 20);
		circle.setFill(Color.CHARTREUSE);
		Graphics graphics = new Graphics(Globals.treeRepresentationGraphicsContext);
		gameObject.addComponent(graphics);

		Sprite sprite = new Sprite();
		sprite.addShape(new Circle(100, 100, 100));
		gameObject.addComponent(sprite);
	}

	// SearchNode
	public void visit(SearchNode searchNode) {
		super.visit(searchNode);
		searchNode.addComponent(new Position());
		searchNode.addComponent(new Graphics(Globals.treeRepresentationGraphicsContext));

		Circle circle = new Circle(0, 0, 10);
		circle.setFill(Color.RED);
		Sprite spriteComponent = new Sprite();

		spriteComponent.addShape(circle);
		searchNode.addComponent(spriteComponent);

		Coloring coloring = new Coloring();
		searchNode.addComponent(coloring);

		searchNode.addComponent(new Animation(1));

		Text text = new Text();
		String actionText = searchNode.getAction();
		actionText = actionText == null ? "-" : "" + actionText.charAt(0);
		text.setText("S: " + searchNode.getState().toString() + "\n " + "C: " + searchNode.getPathCost() + "\n A: "
				+ actionText);
		searchNode.addComponent(text);

		if (searchNode.getState().getProblem().isGoalState(searchNode.getState())) {
			coloring.setColor(Color.RED);
		} else {
			coloring.setColor(Color.BLUE);
		}

		if (!searchNode.isRoot()) {
			searchNode.getComponent(Position.class)
					.directSetPosition(searchNode.getParent().getComponent(Position.class).getPosition());
			// assoziation (linie zum parent)
			searchNode.addComponent(new Association(searchNode.getParent()));
			searchNode.addComponent(new ConnectionLine());
			// add treeComponent
			TreeComponent treeComponent = new TreeComponent(searchNode.getParent().getComponent(TreeComponent.class));
			searchNode.addComponent(treeComponent);
			searchNode.getParent().getComponent(TreeLayouter.class).layout();
		} else {
			TreeComponent treeComponent = new TreeComponent();
			searchNode.addComponent(treeComponent);
		}

		TreeLayouter treeLayouter = new TreeLayouter();
		searchNode.addComponent(treeLayouter);

		InputHandler ihhover = new InputHandler(e -> {
			System.out.println("INPUT SearchNode");
			try {
				// searchNode.getState().getComponent(Graphics.class).clearPane();
				searchNode.getState().getProblem().getComponent(Graphics.class).show();
				searchNode.getState().getComponent(Graphics.class).show();
				searchNode.getPath().getComponent(Graphics.class).show();
				searchNode.getComponent(TreeLayouter.class).layout();
			} catch (Exception exeption) {
				System.out.println("Components Missing");
			}

			if (e != null) {
				e.consume();
			}
			return null;
		});
		searchNode.addComponent(ihhover);
		circle.setOnMouseEntered(InputConnector.getInputConnector(searchNode));
		GameObjectRegistry.registerForLargeComponentUpdate(searchNode);
		searchNode.getComponent(Graphics.class).notifyNewDrawable();
		searchNode.getComponent(Graphics.class).show();

	}

	// Frontier
	public void visit(Frontier gameObject) {
		super.visit(gameObject);
	}

	// PathProblem
	public void visit(RasterPathProblem rasterPathProblem) {
		super.visit(rasterPathProblem);

		rasterPathProblem.addComponent(new Graphics(Globals.stateRepresentationGraphicsContext));
		// can cause createt objects that have not been initialized not apera
		// TODO: change how get all works to realy add all objects
		// Fetch all Frontiers
		List<Frontier> frontiers = GameObjectRegistry.getAllGameObjectsOfType(Frontier.class);
		// Fetch all ExploredSets
		List<ExploredSet> exploredSets = GameObjectRegistry.getAllGameObjectsOfType(ExploredSet.class);

		TileMap2D tilemap = new TileMap2D();
		rasterPathProblem.addComponent(tilemap);

		for (int i = 0; i < rasterPathProblem.GAMESIZE; i++) {
			for (int j = 0; j < rasterPathProblem.GAMESIZE; j++) {
				Rectangle r = new Rectangle();

				if (rasterPathProblem.labyrinth[i][j] == 'e') {
					tilemap.insertTile(new Vector2DInt(i, j), r, Color.WHITE);
				} else {
					tilemap.insertTile(new Vector2DInt(i, j), r, Color.BLUE);
				}
				final int ic = i;
				final int jc = j;
				r.setOnMouseClicked(e -> {
					if (rasterPathProblem.labyrinth[ic][jc] == 'e') {
						rasterPathProblem.labyrinth[ic][jc] = 'w';
						tilemap.insertTile(new Vector2DInt(ic, jc), r, Color.BLUE);
					} else {
						rasterPathProblem.labyrinth[ic][jc] = 'e';
						tilemap.insertTile(new Vector2DInt(ic, jc), r, Color.WHITE);
					}
				});
			}
		}

		Sprite sprites = new Sprite();
		rasterPathProblem.addComponent(sprites);

		Circle goalCircle = new Circle();
		goalCircle.setFill(Color.RED);
		tilemap.fitToTilemap(((RasterPathState) rasterPathProblem.getGoalState()).getPosition(), goalCircle);

		Circle startCircle = new Circle();
		startCircle.setFill(Color.GREEN);
		tilemap.fitToTilemap(((RasterPathState) rasterPathProblem.getInitialState()).getPosition(), startCircle);

		sprites.addShape(goalCircle);
		sprites.addShape(startCircle);

		rasterPathProblem.getComponent(Graphics.class).show();
	}

	public void visit(RasterPathState rasterPathState) {
		super.visit(rasterPathState);

		/// KOMMT NICHT AN PARENT STATES RAN UM LÖSUNG ANZUZEIGEN MUSS ÜBER EXTERNE
		/// LÖSUNG PASIEREN
		Globals.stateRepresentationGraphicsContext.getChildren().clear();

		Graphics problem = rasterPathState.getProblem().getComponent(Graphics.class);

		problem.show();

		Component position = new Position(Vector2D.ZERO);
		rasterPathState.addComponent(position);

		Circle stateC = new Circle();
		rasterPathState.getProblem().getComponent(TileMap2D.class).fitToTilemap(rasterPathState.getPosition(), stateC);
		stateC.setRadius(4);
		stateC.setFill(Color.CYAN);

		Graphics g = new Graphics(Globals.stateRepresentationGraphicsContext);
		rasterPathState.addComponent(g);

		Sprite sprite = new Sprite();
		rasterPathState.addComponent(sprite);
		sprite.addShape(stateC);

		g.show();

	}

	private void visit(Path path, RasterPathProblem prob) {
		path.addComponent(new Position());
		Graphics g = new Graphics(Globals.stateRepresentationGraphicsContext);
		path.addComponent(g);

		Sprite sprite = new Sprite();
		path.addComponent(sprite);

		List<State> states = path.getVisitedStates();
		List<String> actions = path.getPathActions();

		TileMap2D tileM = new TileMap2D();
		if (prob.hasComponent(TileMap2D.class)) {
			tileM = prob.getComponent(TileMap2D.class);
		}

		if (states.size() >= 2) {
			RasterPathState statePrev = (RasterPathState) states.get(0);
			RasterPathState stateSucc = (RasterPathState) states.get(0);
			for (int i = 1; i < states.size(); i++) {
				statePrev = stateSucc;
				stateSucc = (RasterPathState) states.get(i);

				Vector2D startLine = tileM.fitToTilemap(new Vector2DInt(statePrev.getPosition()), null);
				Vector2D endLine = tileM.fitToTilemap(new Vector2DInt(stateSucc.getPosition()), null);

				Line line = new Line(startLine.x, startLine.y, endLine.x, endLine.y);
				line.setStroke(new Color(1.0, 0, 0, 0.3));
				sprite.addShape(line);

			}
		}

		System.out.println("" + actions);
		Text t = new Text(actions.toString());
		path.addComponent(t);

		g.show();

	}

}
