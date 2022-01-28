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
import tools.Vector2D;
import tools.Vector2DInt;

public class TileMap2D extends Drawable {

	private int tileSize = 20;
	private int borderWith = 1;

	HashMap<Vector2DInt, Shape> tiles;

	public TileMap2D() {
		this.tiles = new HashMap<Vector2DInt, Shape>();
	}

	public TileMap2D(int tileSize, int borderWith) {
		this();
		this.tileSize = tileSize;
		this.borderWith = borderWith;

	}

	public Shape getTile(Vector2DInt coords) {
		return tiles.get(coords);
	}

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
	
	public void setTileColor(Vector2DInt coords, Color color) {
		Shape shape = tiles.get(coords);
		if(shape == null) {
			return;
		}
		shape .setFill(color);
		shape.setStyle(" -fx-stroke: black; -fx-stroke-width: " + this.borderWith + ";");
		//this.tiles.put(coords, shape);
		if (this.entity != null) {
			this.entity.getComponent(Graphics.class).notifyNewDrawable();
		}
	}

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
