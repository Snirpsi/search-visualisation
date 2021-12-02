package ecs.components.graphics;

import ecs.GameObject;
import ecs.MissingComponentExeption;
import ecs.components.Association;
import ecs.components.Position;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineGraphics extends Graphics {

	public LineGraphics(Pane graphicsContext) {
		super(graphicsContext);
	}

	@Override
	public void clicked() {
	}

	@Override
	public void update(float deltaT) throws MissingComponentExeption {
		Position ownPos = super.entity.getComponent(Position.class);
		if (ownPos == null) {
			throw new MissingComponentExeption(
					super.entity.getName() + " does not contain " + Position.class.getName());
		}
		Association ownAssociation = super.entity.getComponent(Association.class);
		if (ownAssociation == null) {
			throw new MissingComponentExeption(
					super.entity.getName() + " does not contain " + Association.class.getName());
		}
		GameObject other = ownAssociation.other;
		if (other == null) {
			throw new NullPointerException();
		}
		Position otherPos = other.getComponent(Position.class);
		if (otherPos == null) {
			throw new MissingComponentExeption();
		}

		for (Shape shape : shapes) {
			if (shape.getClass().isAssignableFrom(Line.class)) {
				Line l = Line.class.cast(shape);
				l.setStartX(ownPos.getPosition().x);
				l.setStartY(ownPos.getPosition().y);
				l.setEndX(otherPos.getPosition().x);
				l.setEndY(otherPos.getPosition().y);
			}
		}
	}

	@Override
	public void fetchDependencies() {
	}


}
