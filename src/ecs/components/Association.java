package ecs.components;

import ecs.Component;
import ecs.GameObject;
import ecs.components.graphics.LineGraphics;

/**
 * This class represents a {@link Association} between two {@link GameObject}s
 * (entities). It is used by the {@link LineGraphics} {@link Component} to draw a
 * connecting line between the two {@link Position}s of the associated objects.
 * 
 * @author Severin Dippold
 */
public class Association extends Component {

	public GameObject other;

	public Association() {
		this.other = null;
	}

	public Association(GameObject other) {
		this.other = other;
	}

	public void associateWith(GameObject other) {
		this.other = other;
	}

	@Override
	public void update(float deltaT) {
	}
}
