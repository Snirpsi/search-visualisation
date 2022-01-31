package ecs.components.graphics;

import ecs.Component;
import ecs.components.graphics.drawables.Sprite;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
/**
 * determines the collor of a game object
 * @author Severin
 *
 */
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
