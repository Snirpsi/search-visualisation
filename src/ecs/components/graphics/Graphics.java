package ecs.components.graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import application.Globals;
import ecs.Component;
import ecs.components.Position;
import ecs.components.graphics.drawables.ConnectionLine;
import ecs.components.graphics.drawables.Sprite;
import ecs.components.graphics.drawables.Text;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import tools.Vector2D;

public class Graphics extends Component {
	// TODO: Refactor! sollte sich grafische objekte selbstständig holen (Lines,
	// Sprites, Tilemaps ...)

	// public ArrayList<Shape> shapes = null;
	Pane graphicsContext = null;

	private Pane pane = null;
	public List<Node> drawables = null;

	public Graphics() { // abstrakt
		this.graphicsContext = Globals.treeRepresentationGraphicsContext;
		this.drawables = new LinkedList<Node>();
		this.pane = new Pane();
	}

	public Graphics(Pane graphicsContext) { // abstrakt
		this.graphicsContext = graphicsContext;
		this.drawables = new LinkedList<Node>();
		this.pane = new Pane();
		notifyNewDrawable();
	}

	@Override
	public void update(float deltaT) {
		Vector2D pos = this.entity.getComponent(Position.class).getPosition();
		pane.setTranslateX(pos.x);
		pane.setTranslateY(pos.y);
	}

	public void show() {
		this.hide();// necessery because in javafx you cant insert duplications of nodes
		graphicsContext.getChildren().add(pane);
	}

	public void hide() {
		graphicsContext.getChildren().remove(pane);
	}

	public void clearPane() {
		graphicsContext.getChildren().clear();
		pane.getChildren().clear();
	}

	public void notifyNewDrawable() {
		if (this.entity == null) {
			return;
		}
		// collect all drawables
		List<Node> newDrawables = new LinkedList<Node>();
		
		if (entity.hasComponent(TileMap2D.class)) {
			newDrawables.addAll(entity.getComponent(TileMap2D.class).getShapes());
		}
		
		if (entity.hasComponent(Sprite.class)) {
			newDrawables.addAll(entity.getComponent(Sprite.class).getShapes());
		}

		if (entity.hasComponent(ConnectionLine.class)) {
			newDrawables.addAll(entity.getComponent(ConnectionLine.class).getShapes());
		}

		if (entity.hasComponent(Text.class)) {
			newDrawables.addAll(entity.getComponent(Text.class).getShapes());
		}
		
		this.pane.getChildren().clear();
		this.pane.getChildren().addAll(newDrawables);
		this.drawables = newDrawables;

		this.update(0);// instant update of graphics to remove flikering
	}
}
