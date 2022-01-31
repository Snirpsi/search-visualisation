package ecs.components.graphics.drawables;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ecs.components.graphics.Drawable;
import ecs.components.graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import settings.Settings;
import tools.Vector2D;
import tools.Vector2DInt;

public class TileMap2D extends Drawable {

	private int tileSize = Settings.TILEMAP.TILE_SIZE;
	private int borderWith = Settings.TILEMAP.BORDER_WITH;

	/**
	 * storage Datastructure
	 */
	HashMap<Vector2DInt, Shape> tiles;

	/**
	 * Constructor
	 */
	public TileMap2D() {
		this.tiles = new HashMap<Vector2DInt, Shape>();
	}

	/**
	 * Constructor with size
	 */
	public TileMap2D(int tileSize, int borderWith) {
		this();
		this.tileSize = tileSize;
		this.borderWith = borderWith;

	}

	/**
	 * returns a specific tile with the matching coordinates
	 * 
	 * @param coordinates of a sector a tile is in
	 * @return
	 */
	public Shape getTile(Vector2DInt coords) {
		return tiles.get(coords);
	}

	/**
	 * Method to insert tiles into the {@link TileMap2D}
	 * 
	 * @param coords where?
	 * @param shape  what?
	 * @param color  color?
	 * 
	 */
	public void insertTile(Vector2DInt coords, Shape shape, Color color) {
		shape.setFill(color);
		shape.setStyle(" -fx-stroke: black; -fx-stroke-width: " + this.borderWith + ";");
		this.tiles.put(coords, shape);
		if (shape instanceof Rectangle r) {
			r.setWidth(tileSize);
			r.setHeight(tileSize);
			r.setX(coords.x * tileSize);
			r.setY(coords.y * tileSize);
		} else if (shape instanceof Circle c) {
			c.setCenterX(coords.x * tileSize + tileSize / 2);
			c.setCenterY(coords.y * tileSize + tileSize / 2);
			c.setRadius(tileSize / 2);
		}

		if (this.entity != null) {
			this.entity.getComponent(Graphics.class).notifyNewDrawable();
		}
	}

	/**
	 * changes the tile color of a specific tile in the {@link TileMap2D}
	 * 
	 * @param coords
	 * @param color
	 */
	public void setTileColor(Vector2DInt coords, Color color) {
		Shape shape = tiles.get(coords);
		if (shape == null) {
			return;
		}
		shape.setFill(color);
		shape.setStyle(" -fx-stroke: black; -fx-stroke-width: " + this.borderWith + ";");
		// this.tiles.put(coords, shape);
		if (this.entity != null) {
			this.entity.getComponent(Graphics.class).notifyNewDrawable();
		}
	}

	/**
	 * Fits a node to a corresponding tile on the {@link TileMap2D}.
	 * 
	 * @param coords
	 * @param nodeFX
	 * @return coordinates that fit eg. a Sprite on a specific tile from the map
	 * 
	 */
	public Vector2D fitToTilemap(Vector2DInt coords, Node nodeFX) {
		Vector2D fitCoord = null;
		if (nodeFX == null) {
			return fitCoord = new Vector2D(coords.x, coords.y).mul(tileSize)
					.add(new Vector2D(tileSize / 2, tileSize / 2));
		}

		if (nodeFX instanceof Rectangle r) {
			fitCoord = new Vector2D(coords.x, coords.y).mul(tileSize);
			r.setWidth(tileSize);
			r.setHeight(tileSize);
			r.setX(fitCoord.x);
			r.setX(fitCoord.y);
		} else if (nodeFX instanceof Circle c) {
			fitCoord = new Vector2D(coords.x, coords.y).mul(tileSize).add(new Vector2D(tileSize / 2, tileSize / 2));
			c.setRadius(tileSize / 2);
			c.setCenterX(fitCoord.x);
			c.setCenterY(fitCoord.y);
		} else if (nodeFX instanceof Label l) {
			// fitCoord = new Vector2D(coords.x - l.getWidth() / 2, coords.y - l.getHeight()
			// / 2).mul(tileSize);
		}
		return fitCoord;
	}

	@Override
	public void update(float deltaT) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Node> getShapes() {
		List<Node> ret = new LinkedList<>();
		ret.addAll(tiles.values());
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
