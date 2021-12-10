package ecs.visitors;

import java.util.List;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.SearchNode;
import ai_algorithm.Solution;
import ai_algorithm.State;
import ai_algorithm.problems.Problem;
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
import ecs.components.graphics.LineGraphics;
import ecs.components.graphics.SpriteGraphics;
import ecs.components.graphics.TreeLayouter;
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
		if (gameObject instanceof Solution s) {
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
		Graphics graphics = new SpriteGraphics(Globals.treeRepresentationGraphicsContext);
		graphics.addShape(circle);
		gameObject.addComponent(graphics);
	}

	private void visit(Solution gameObject, RasterPathProblem prob) {

		SpriteGraphics spriteGrapics = new SpriteGraphics(Globals.stateRepresentationGraphicsContext);
		List<State> states = gameObject.getVisitedStates();
		List<String> actions = gameObject.getSolutionActions();
		int offset = (prob.TILESIZE / 2);
		int transform = (prob.TILESIZE);

		if (states.size() >= 2) {

			RasterPathState statePrev = (RasterPathState) states.get(0);
			RasterPathState stateSucc = (RasterPathState) states.get(0);
			for (int i = 1; i < states.size(); i++) {
				// Linie malen
				statePrev = stateSucc;
				stateSucc = (RasterPathState) states.get(i);
				Line path = new Line(statePrev.getPosition().x * transform + offset,
						statePrev.getPosition().y * transform + offset, stateSucc.getPosition().x * transform + offset,
						stateSucc.getPosition().y * transform + offset);
				path.setStroke(new Color(1.0, 0, 0, 0.3));
				spriteGrapics.addShape(path);
//				Label action = new Label(actions.get(i - 1));
//				Vector2D labelPos = new Vector2D(statePrev.getPosition().x * transform + offset,
//						statePrev.getPosition().y * transform + offset)
//								.interpolate(new Vector2D(stateSucc.getPosition().x * transform + offset,
//										stateSucc.getPosition().y * transform + offset), 0.5);
//				action.setTranslateX(labelPos.x);
//				action.setTranslateY(labelPos.y);
				// Shape a = action. ja mistikack .... was mach ich denn jetzt mit dem label
				// spriteGrapics.addShape(action);

			}
		}

		gameObject.addComponent(spriteGrapics);

	}

	// SearchNode
	public void visit(SearchNode gameObject) {
		super.visit(gameObject);

		// not so default
		System.out.println("Searchnode Visitor");
		gameObject.addComponent(new Position(new Vector2D((float) Math.random() * 1000, (float) Math.random() * 1000)));

		var sprites = new SpriteGraphics(Globals.treeRepresentationGraphicsContext);
		Circle circle = new Circle(0, 0, 10);
		sprites.addShape(circle);
		gameObject.addComponent(sprites);

		var coloring = new Coloring();
		gameObject.addComponent(coloring);

		if (gameObject.getState().getProblem().isGoalState(gameObject.getState())) {
			System.out.println("SET COLLOR RED");
			coloring.setColor(Color.RED);
		} else {
			coloring.setColor(Color.BLUE);
		}
		gameObject.addComponent(new Animation(1));

		if (gameObject.getParent() != null) {

			gameObject.getComponent(Position.class)
					.directSetPosition(gameObject.getParent().getComponent(Position.class).getPosition());
			// Verbindung zum parent (LINIE)
			System.out.println("initialize line");
			gameObject.addComponent(new Association(gameObject.getParent()));

			var linesGraphics = new LineGraphics(Globals.treeRepresentationGraphicsContext);
			linesGraphics.addShape(new Line());
			gameObject.addComponent(linesGraphics);

			/// Baum Komponente
			var treeComponent = new TreeComponent(gameObject.getParent().getComponent(TreeComponent.class));
			gameObject.addComponent(treeComponent);

			var parentLayouter = gameObject.getParent().getComponent(TreeLayouter.class);
			if (parentLayouter != null) {
				parentLayouter.layout();
			}

		} else {
			var treeComponent = new TreeComponent();
			gameObject.addComponent(treeComponent);
		}

		// BaumLayout
		TreeLayouter treeLayouter = new TreeLayouter();
		gameObject.addComponent(treeLayouter);

		InputHandler ih = new InputHandler(e -> {
			System.out.println("INPUT SearchNode");
			try {
				gameObject.getState().getComponent(SpriteGraphics.class).clearPane();
				gameObject.getState().getProblem().getComponent(SpriteGraphics.class).show();
				gameObject.getState().getComponent(SpriteGraphics.class).show();
				gameObject.getSolutionActions().getComponent(SpriteGraphics.class).show();
				gameObject.getComponent(TreeLayouter.class).layout();
			} catch (Exception exeption) {
				System.out.println("Components Missing");
			}

			e.consume();
			return null;
		});
		gameObject.addComponent(ih);
		circle.setOnMouseClicked(InputConnector.getInputConnector(gameObject));
		InputHandler ihhover = new InputHandler(e -> {
			System.out.println("INPUT SearchNode");
			try {
				gameObject.getState().getComponent(SpriteGraphics.class).clearPane();
				gameObject.getState().getProblem().getComponent(SpriteGraphics.class).show();
				gameObject.getState().getComponent(SpriteGraphics.class).show();
				gameObject.getSolutionActions().getComponent(SpriteGraphics.class).show();
				gameObject.getComponent(TreeLayouter.class).layout();
			} catch (Exception exeption) {
				System.out.println("Components Missing");
			}

			e.consume();
			return null;
		});
		gameObject.addComponent(ihhover);
		circle.setOnMouseEntered(InputConnector.getInputConnector(gameObject));
		GameObjectRegistry.registerForLargeComponentUpdate(gameObject);
	}

	// Frontier
	public void visit(Frontier gameObject) {
		super.visit(gameObject);

	}

	// PathProblem
	public void visit(RasterPathProblem rasterPathProblem) {
		super.visit(rasterPathProblem);

		// can cause createt objects that have not been initialized not apera
		// TODO: change how get all works to realy add all objects
		// Fetch all Frontiers
		List<Frontier> frontiers = GameObjectRegistry.getAllGameObjectsOfType(Frontier.class);
		// Fetch all ExploredSets
		List<ExploredSet> exploredSets = GameObjectRegistry.getAllGameObjectsOfType(ExploredSet.class);

		SpriteGraphics spriteComponent = new SpriteGraphics(Globals.stateRepresentationGraphicsContext);

		for (int i = 0; i < rasterPathProblem.GAMESIZE; i++) {
			for (int j = 0; j < rasterPathProblem.GAMESIZE; j++) {
				Rectangle r = new Rectangle(i * rasterPathProblem.TILESIZE, j * rasterPathProblem.TILESIZE,
						rasterPathProblem.TILESIZE, rasterPathProblem.TILESIZE);
				if (rasterPathProblem.labyrinth[i][j] == 'e') {
					r.setFill(new Color(1.0, 1.0, 1.0, 1.0));
					RasterPathState rps = (RasterPathState) rasterPathProblem
							.getCorresphrondingState(new Vector2DInt(i, j));
					if (rps != null) {
						for (Frontier frontier : frontiers) {
							if (frontier.containsNodeWithState(rps)) {
								r.setFill(Color.LIGHTGREEN);
							}
						}
						for (ExploredSet exploredSet : exploredSets) {
							if (exploredSet.contains(rps)) {
								r.setFill(Color.LIGHTGRAY);
							}
						}
					}
				} else {
					r.setFill(new Color(0.2, 0.2, 1.0, 1.0));
				}
				r.setStyle(" -fx-stroke: black; -fx-stroke-width: " + rasterPathProblem.BORDERSIZE + ";");
				spriteComponent.addShape(r);
			}
		}

		var goalpos = (rasterPathProblem.GAMESIZE - 1) * rasterPathProblem.TILESIZE + rasterPathProblem.TILESIZE / 2;
		Circle goalCircle = new Circle(goalpos, goalpos, 10);
		goalCircle.setStyle(" -fx-stroke: black; -fx-stroke-width: " + rasterPathProblem.BORDERSIZE + ";");
		goalCircle.setFill(Color.RED);
		spriteComponent.addShape(goalCircle);
		rasterPathProblem.addComponent(spriteComponent);
		spriteComponent.hide();

	}

	public void visit(RasterPathState RasterPathState) {
		super.visit(RasterPathState);

		/// KOMMT NICHT AN PARENT STATES RAN UM LÖSUNG ANZUZEIGEN MUSS ÜBER EXTERNE
		/// LÖSUNG PASIEREN
		Globals.stateRepresentationGraphicsContext.getChildren().clear();

		var problem = RasterPathState.getProblem().getComponent(SpriteGraphics.class);
		if (problem != null) {
			problem.show();
		}

		Component position = new Position(Vector2D.ZERO);
		RasterPathState.addComponent(position);

		double fetchedTilesize = RasterPathState.getProblem().TILESIZE;
		Circle startC = new Circle(RasterPathState.getPosition().x * fetchedTilesize + fetchedTilesize / 2,
				RasterPathState.getPosition().y * fetchedTilesize + fetchedTilesize / 2, 7);
		startC.setFill(Color.CYAN);
		startC.setStyle(" -fx-stroke: black; -fx-stroke-width: " + RasterPathState.getProblem().BORDERSIZE + ";");
		SpriteGraphics srg = new SpriteGraphics(Globals.stateRepresentationGraphicsContext);
		srg.addShape(startC);
		RasterPathState.addComponent(srg);

	}
}
