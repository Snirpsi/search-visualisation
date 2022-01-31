package ecs.components;

import ecs.Component;
import tools.Vector2D;

/**
 * This class represents the position of a GameObject.
 * 
 * @author Severin
 *
 */
public class Position extends Component {
	/**
	 * position the object has in parent javafx plane
	 */
	private Vector2D position;

	/**
	 * constructor
	 */
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
