package ecs.components.graphics;

import ecs.MissingComponentExeption;
import ecs.components.Association;
import ecs.components.Position;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class SpriteGraphics extends Graphics {

	// Coloring coloring = null;
	Position position = null;

	public SpriteGraphics(Pane graphicsContext) {
		super(graphicsContext);
	}

	@Override
	public void clicked() {
	}

	@Override
	public void update(float deltaT) {
		if (this.position == null) {
			fetchDependencies();
			return;
		}
		for (Shape shape : shapes) {
//			if (coloring != null) {
//				shape.setFill(this.coloring.color);
//			}
			shape.setLayoutX(position.getPosition().x);
			shape.setLayoutY(position.getPosition().y);
		}

	}

	@Override
	public void fetchDependencies() {
		// this.coloring = super.entitie.getComponent(Coloring.class);
		this.position = super.entity.getComponent(Position.class);
		if (this.position == null) {
		//	throw new MissingComponentExeption(super.entity.getName() + " does not contain " + Association.class.getName());
		}
	}
}
