package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import application.Globals;
import ecs.components.Association;
import ecs.components.Position;
import ecs.components.graphics.Drawable;
import ecs.components.graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Sprite extends Drawable {

	// Coloring coloring = null;

	LinkedList<Shape> sprites;

	public Sprite() {
		this.sprites = new LinkedList<Shape>();
	}

	public void addShape(Shape shape) {
		sprites.addFirst(shape);
		if (entity != null) {
			entity.getComponent(Graphics.class).notifyNewDrawable();
		}
	}

	@Override
	public void update(float deltaT) {
	}

	@Override
	public List<Node> getShapes() {
		return new LinkedList<Node>(sprites);
	}

}
