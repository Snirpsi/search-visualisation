package ecs.visitors;

import java.util.List;

import ai_algorithm.Frontier;
import ai_algorithm.RasterPathProblem;
import ai_algorithm.RasterPathState;
import ai_algorithm.SearchNode;
import ai_algorithm.Solution;
import ai_algorithm.State;
import application.Globals;
import application.UpdateRegistry;
import ecs.Component;
import ecs.GameObject;
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
			} catch (Exception exeption) {
				System.out.println("Components Missing");
			}

			e.consume();
			return null;
		});
		gameObject.addComponent(ih);
		circle.setOnMouseClicked(InputConnector.getInputConnector(gameObject));
		UpdateRegistry.registerForLargeComponentUpdate(gameObject);
	}

	// Frontier
	public void visit(Frontier gameObject) {
		super.visit(gameObject);

	}

	// PathProblem
	public void visit(RasterPathProblem gameObject) {
		super.visit(gameObject);
		// Globals.stateRepresentationGraphicsContext.getChildren().clear();

		SpriteGraphics spriteComponent = new SpriteGraphics(Globals.stateRepresentationGraphicsContext);

		for (int i = 0; i < gameObject.GAMESIZE; i++) {
			for (int j = 0; j < gameObject.GAMESIZE; j++) {
				Rectangle r = new Rectangle(i * gameObject.TILESIZE, j * gameObject.TILESIZE, gameObject.TILESIZE,
						gameObject.TILESIZE);
				if (gameObject.labyrinth[i][j] == 'e') {
					r.setFill(new Color(1.0, 1.0, 1.0, 1.0));
				} else {
					r.setFill(new Color(0.2, 0.2, 1.0, 1.0));
				}
				r.setStyle(" -fx-stroke: black; -fx-stroke-width: " + gameObject.BORDERSIZE + ";");
				spriteComponent.addShape(r);

			}
		}

		var goalpos = (gameObject.GAMESIZE - 1) * gameObject.TILESIZE + gameObject.TILESIZE / 2;
		Circle goalCircle = new Circle(goalpos, goalpos, 10);
		goalCircle.setStyle(" -fx-stroke: black; -fx-stroke-width: " + gameObject.BORDERSIZE + ";");
		goalCircle.setFill(Color.RED);
		spriteComponent.addShape(goalCircle);

		gameObject.addComponent(spriteComponent);
		spriteComponent.hide();

	}

	public void visit(RasterPathState gameObject) {
		super.visit(gameObject);

		/// KOMMT NICHT AN PARENT STATES RAN UM LÖSUNG ANZUZEIGEN MUSS ÜBER EXTERNE
		/// LÖSUNG PASIEREN
		Globals.stateRepresentationGraphicsContext.getChildren().clear();

		var problem = gameObject.getProblem().getComponent(SpriteGraphics.class);
		if (problem != null) {
			problem.show();
		}

		Component position = new Position(Vector2D.ZERO);
		gameObject.addComponent(position);

		double fetchedTilesize = gameObject.getProblem().TILESIZE;
		Circle startC = new Circle(gameObject.getPosition().x * fetchedTilesize + fetchedTilesize / 2,
				gameObject.getPosition().y * fetchedTilesize + fetchedTilesize / 2, 10);
		startC.setFill(Color.CYAN);
		SpriteGraphics srg = new SpriteGraphics(Globals.stateRepresentationGraphicsContext);
		srg.addShape(startC);
		gameObject.addComponent(srg);

	}
}
