package ecs.components;

import ecs.Component;

import tools.Vector2D;

/**
 * This class represetns the position of a Gameobect.
 * @author Severin
 *
 */
public class Position extends Component {

	private Vector2D position;

	public Position() {
		this.position = new Vector2D();
	}

	public Position(Vector2D position) {
		this.position = position;
	}

	@Override
	public void update(float deltaT) {
	}

	public void setPosition(Vector2D position) {
		if (!this.entity.hasComponent(Animation.class)) {
			this.position = position;
			return;
		}
		var animator = this.entity.getComponent(Animation.class);
		animator.setEndPos(position);
	}

	public void directSetPosition(Vector2D position) {
		this.position = position;

		if (!this.entity.hasComponent(Animation.class)) {
			return;
		}
		var animator = this.entity.getComponent(Animation.class);
		animator.startPos = position;
	}

	public Vector2D getPosition() {


		return position;

	}

	
	public Vector2D getFuturePosition() {
		if (super.entity.hasComponent(Animation.class)) {
			return super.entity.getComponent(Animation.class).endPos;
		}
		return position;
	}

}
