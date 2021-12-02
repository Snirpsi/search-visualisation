package ecs.components.graphics;

import ecs.Component;

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

		if (this.entity == null) {
			return;
		}

		Graphics gc = this.entity.getComponent(SpriteGraphics.class);

		if (gc == null) {
			System.err.println("No Graphics Component found");
			return;
		}

		for (Shape shape : gc.shapes) {
			shape.setFill(this.color);
		}
	}

	@Override
	public void update(float deltaT) {
		// TODO: dirty Bit abfragen
	}

	@Override
	public void fetchDependencies() {
		// TODO Auto-generated method stub
	}
}
