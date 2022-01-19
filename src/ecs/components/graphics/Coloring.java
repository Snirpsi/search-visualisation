package ecs.components.graphics;

import ecs.Component;
import ecs.components.graphics.drawables.Sprite;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Coloring extends Component {

	public Color color;

	public Coloring() {
		super();
		this.color = Color.BLACK;
	}

	public void setColor(Color color) {
		this.color = color;

		if (!this.entity.hasComponent(Sprite.class)) {
			return;
		}

		Sprite sprite = this.entity.getComponent(Sprite.class);

		for (Node shape : sprite.getShapes()) {
			if (shape instanceof Shape) {
				((Shape) shape).setFill(this.color);
			}
		}
	}

	@Override
	public void update(float deltaT) {

	}

}
