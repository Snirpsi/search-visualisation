package ecs.components.graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import application.Globals;
import ecs.Component;
import ecs.components.Position;
import ecs.components.graphics.drawables.ConnectionLine;
import ecs.components.graphics.drawables.Sprite;
import ecs.components.graphics.drawables.Text;
import ecs.components.graphics.drawables.TileMap2D;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import tools.Vector2D;

public class Graphics extends Component {
	// TODO: Refactor! sollte sich grafische objekte selbstständig holen (Lines,
	// Sprites, Tilemaps ...)

	/**
	 * The grapical context the entity is displayed on
	 */
	Pane graphicsContext = null;

	/**
	 * the pane where all Drawables are drawn on
	 */
	private Pane pane = null;

	/**
	 * a list of all drawables the component has the drawables are registered in the
	 * notifyNewDrawable method The Grapics Component is one of few components that
	 * has a reference on other components of the game object.
	 */
	public List<Node> drawables = null;

	/**
	 * creates a new Grapics component on the default pane
	 */
	public Graphics() { // abstrakt
		this.graphicsContext = Globals.treeRepresentationGraphicsContext;
		this.drawables = new LinkedList<Node>();
		this.pane = new Pane();
		this.pane.setPickOnBounds(false);
	}

	/**
	 * allows the pane to be specified
	 * 
	 * @param graphicsContext
	 */
	public Graphics(Pane graphicsContext) { // abstrakt
		this.graphicsContext = graphicsContext;
		this.drawables = new LinkedList<Node>();
		this.pane = new Pane();
		notifyNewDrawable();
		this.pane.setPickOnBounds(false);
	}

	/**
	 * updates the position of the drawn object
	 */
	@Override
	public void update(float deltaT) {
		Vector2D pos = new Vector2D();
		if (this.entity.hasComponent(Position.class)) {
			pos = this.entity.getComponent(Position.class).getPosition();
		}

		pane.setTranslateX(pos.x);
		pane.setTranslateY(pos.y);
	}

	/**
	 * displays the object to the screen
	 */
	public void show() {
		this.hide();// necessery because in javafx you cant insert duplications of nodes
		graphicsContext.getChildren().add(pane);
		// quickfix
		if (this.graphicsContext == Globals.treeRepresentationGraphicsContext) {
			this.pane.toBack();
			// this.pane.setViewOrder(-2000000);

			// to debug javaFX fills
			// this.pane.setBackground(new Background(new BackgroundFill(new Color(0.5, 0.5,
			// 0.5, 0.1), null, null)));
		}
	}

	/**
	 * removes the objects representation
	 */
	public void hide() {
		graphicsContext.getChildren().remove(pane);
	}

	/**
	 * hides all drawings on a grapcal contect
	 */
	public void clearPane() {
		graphicsContext.getChildren().clear();
		pane.getChildren().clear();
	}

	/**
	 * registers for a new drawable 
	 */
	public void notifyNewDrawable() {
		if (this.entity == null) {
			return;
		}
		// collect all drawables
		List<Node> newDrawables = new LinkedList<Node>();

		if (entity.hasComponent(TileMap2D.class)) {
			newDrawables.addAll(entity.getComponent(TileMap2D.class).getShapes());
		}

		if (entity.hasComponent(Sprite.class)) {
			newDrawables.addAll(entity.getComponent(Sprite.class).getShapes());
		}

		if (entity.hasComponent(ConnectionLine.class)) {
			newDrawables.addAll(entity.getComponent(ConnectionLine.class).getShapes());

		}

		if (entity.hasComponent(Text.class)) {
			newDrawables.addAll(entity.getComponent(Text.class).getShapes());
		}

		this.pane.getChildren().clear();
		this.pane.getChildren().addAll(newDrawables);
		this.drawables = newDrawables;

		// this.update(0);// instant update of graphics to remove flikering
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
