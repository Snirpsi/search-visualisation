package ecs.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ecs.Component;
import ecs.components.graphics.Graphics;
import javafx.scene.shape.Shape;
import tools.Vector2DInt;

public class TileMap2D extends Component {

	HashMap<Vector2DInt, Shape> tiles;

	public TileMap2D() {
		this.tiles = new HashMap<Vector2DInt, Shape>();
	}

	public Shape getTile(Vector2DInt coords) {
		return tiles.get(coords);
	}
	
	
	public List<Shape> getAllShapes(){
		List<Shape> ret = new LinkedList<>();
		ret.addAll(tiles.values());
		return ret;
	}

	@Override
	public void update(float deltaT) {
		// TODO Auto-generated method stub

	}



}
