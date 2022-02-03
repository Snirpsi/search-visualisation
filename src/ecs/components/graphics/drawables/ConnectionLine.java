package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import ecs.GameObject;
import ecs.components.Association;
import ecs.components.Position;
import ecs.components.graphics.Drawable;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import tools.Vector2D;

/**
 * a class that can draw lines between two game objects
 * 
 * @author Severin
 *
 */
public class ConnectionLine extends Drawable {

	Line connection;

	public ConnectionLine() {
		this.connection = new Line(0, 0, 0, 0);
		connection.setStyle("-fx-stroke: DimGray;");
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
/*
 * Copyright (c) 2022 Severin Dippold
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
