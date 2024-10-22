package ecs.components;

import ecs.Component;
import ecs.GameObject;
import ecs.components.graphics.drawables.ConnectionLine;

/**
 * This class represents a {@link Association} between two {@link GameObject}s
 * (entities). It is used by the {@link ConnectionLine} {@link Component} to
 * draw a connecting line between the two {@link Position}s of the associated
 * objects.
 * 
 * @author Severin Dippold
 * @author Alexander (Comments adjusted)
 */
public class Association extends Component {

	public GameObject other;

	/**
	 * Default constructor
	 */
	public Association() {
		this.other = null;
	}

	/**
	 * Constructor with parameter
	 *
	 * @param other The other {@link GameObject} to associate with.
	 */
	public Association(GameObject other) {
		this.other = other;
	}

	/**
	 * Associates this {@link GameObject} with another {@link GameObject}.
	 *
	 * @param other The other {@link GameObject} to associate with.
	 */
	public void associateWith(GameObject other) {
		this.other = other;
	}

	/**
	 * Disassociates this {@link GameObject} with another {@link GameObject}.
	 */
	@Override
	public void update(float deltaT) {
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