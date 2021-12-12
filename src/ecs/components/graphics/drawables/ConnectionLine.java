package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import ecs.GameObject;
import ecs.components.Association;
import ecs.components.Position;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import tools.Vector2D;

public class ConnectionLine extends Drawable {

	Line connection;

	public ConnectionLine() {
		this.connection = new Line(0, 0, 0, 0);
	}

	@Override
	public void update(float deltaT) {
		Position ownPos = entity.getComponent(Position.class);
		if (!super.entity.hasComponent(Association.class)) {
			return;
		}
		Association ownAssociation = super.entity.getComponent(Association.class);
		GameObject other = ownAssociation.other;
		if (other == null) {
			throw new NullPointerException();
		}
		Position otherPos = other.getComponent(Position.class);

		Vector2D relativVector = otherPos.getPosition().sub(ownPos.getPosition());

		connection.setStartX(0);
		connection.setStartY(0);
		connection.setEndX(relativVector.x);
		connection.setEndY(relativVector.y);
	}
	
	

	public List<Node> getShapes() {
		var ret = new LinkedList<Node>();
		ret.add(connection);
		return ret;
	}


}
