package ecs.components;

import ecs.Component;

import tools.Vector2D;

public class Position extends Component {

	private Vector2D position;

	public Position(Vector2D position) {
		this.position = position;
	}

	@Override
	public void update(float deltaT) {
	}

	public void setPosition(Vector2D position) {
		var animator = this.entity.getComponent(Animation.class);
		if (animator == null) {
			this.position = position;
			return;
		}
		animator.setEndPos(position);
	}

	public void directSetPosition(Vector2D position) {
		this.position = position;
		var animator = this.entity.getComponent(Animation.class);
		if (animator == null) {
			return;
		}
		animator.startPos = position;
	}

	public Vector2D getPosition() {
		return position;
	}
}
