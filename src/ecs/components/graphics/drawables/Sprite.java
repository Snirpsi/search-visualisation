package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import ecs.components.graphics.Drawable;
import ecs.components.graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.shape.Shape;


/**
 * can draw shapes on the canvas
 * @author Severin
 *
 */
public class Sprite extends Drawable {

	// Coloring coloring = null;

	LinkedList<Shape> sprites;

	public Sprite() {
		this.sprites = new LinkedList<Shape>();
	}

	public void addShape(Shape shape) {
		sprites.addFirst(shape);
		if (entity != null) {
			entity.getComponent(Graphics.class).notifyNewDrawable();
		}
	}
	@Override
	public void update(float deltaT) {
	}

	@Override
	public List<Node> getShapes() {
		return new LinkedList<Node>(sprites);
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