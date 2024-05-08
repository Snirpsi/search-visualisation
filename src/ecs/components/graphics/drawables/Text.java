package ecs.components.graphics.drawables;

import java.util.LinkedList;
import java.util.List;

import ecs.components.graphics.Drawable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * superclass all drawing classes shall inherrit 
 * @author Severin
 *
 */
public class Text extends Drawable {

	private Label text = null;
	private int fontsize = 4;

	public Text() {
		this.text = new Label("EMPTY TEXT");
		this.text.setFont(new Font(fontsize));
		this.text.setTextFill(Color.BLACK);
		this.text.setMouseTransparent(true);
	}

	public Text(String string) {
		this();
		this.text.setText(string);

	}

	public void setFontSize(int fontsize) {
		this.fontsize = fontsize;
		text.setFont(new Font(fontsize));
	}

	public void setText(String s) {
		this.text.setText(s);
		if (this.entity != null) {
			this.notifyGraphics();
		}
	}

	public void setX(double x) {
		this.text.setTranslateX(x);
	}

	public void setY(double y) {
		this.text.setTranslateY(y);
	}

	public void getLayoutBounds() {
		this.text.getLayoutBounds();
	}

	public double getLayoutBoundsgetWidth() {
		return this.text.getLayoutBounds().getWidth();
	}

	public double getLayoutBoundsgetHeight() {
		return this.text.getLayoutBounds().getHeight();
	}

	public double getWidth() {
		return this.text.getWidth();
	}

	public double getHeight() {
		return this.text.getHeight();
	}

	public void setFill(Color color) {
		this.text.setTextFill(color);
	}

	@Override
	public List<Node> getShapes() {
		LinkedList<Node> retList = new LinkedList<>();
		retList.add(text);
		return retList;
	}

	@Override
	public void update(float deltaT) {
		this.text.setTranslateX(-this.text.getWidth() / 2);
		this.text.setTranslateY(-this.text.getHeight() / 2);
	}

	public String getText() {
		return this.text.getText();
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