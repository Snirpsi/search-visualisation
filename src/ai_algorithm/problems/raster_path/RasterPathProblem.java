package ai_algorithm.problems.raster_path;

import java.util.LinkedList;
import java.util.List;

import ai_algorithm.State;
import ai_algorithm.problems.Problem;
import tools.Vector2DInt;

public class RasterPathProblem extends Problem {

	public final int GAMESIZE = 10;
	public final int TILESIZE = 20;
	public final int BORDERSIZE = 1;

	public char[][] labyrinth;

	Vector2DInt startPos = new Vector2DInt(0, 0);
	Vector2DInt goalPos = new Vector2DInt(GAMESIZE - 1, GAMESIZE - 1);

	public RasterPathProblem() {
		super();

		startPos = new Vector2DInt((int) (Math.random() * GAMESIZE), (int) (Math.random() * GAMESIZE));

		labyrinth = new char[GAMESIZE][GAMESIZE];

		// Level generieren
		for (int i = 0; i < GAMESIZE; i++) {
			for (int j = 0; j < GAMESIZE; j++) {
				if (Math.random() > 0.3) {
					labyrinth[i][j] = 'e';
				} else {
					labyrinth[i][j] = 'w';
				}
			}
		}
		labyrinth[0][0] = 'e';
		labyrinth[GAMESIZE - 1][GAMESIZE - 1] = 'e';
	}

	@Override
	public State getInitialState() {
		return new RasterPathState(this, startPos);
	}

	public State getGoalState() {
		return new RasterPathState(this, goalPos);
	}

	public State getCorresphrondingState(Vector2DInt coordinate) {
		if (!this.testPosition(coordinate)) {
			return null;

		}
		return new RasterPathState(this, coordinate);
	}

	@Override
	public boolean isGoalState(State state) {
		RasterPathState stateC = (RasterPathState) state;
		if (stateC.getPosition().equals(goalPos)) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getActions(State state) {

		LinkedList<String> l = new LinkedList<String>();
		l.add("UP");
		l.add("DOWN");
		l.add("LEFT");
		l.add("RIGHT");
		return l;
	}

	@Override
	public State getSuccessor(State state, String action) {
		RasterPathState stateC = (RasterPathState) state;
		Vector2DInt oldPos = stateC.getPosition();
		Vector2DInt neuPos = new Vector2DInt(oldPos);

		RasterPathState resultRps = new RasterPathState(this, neuPos);
		switch (action) {
		case "UP": {
			neuPos = oldPos.add(Vector2DInt.UP);
			if (testPosition(neuPos)) {
				resultRps.setPosition(neuPos);
			}
			break;

		}
		case "DOWN": {
			neuPos = oldPos.add(Vector2DInt.DOWN);
			if (testPosition(neuPos)) {
				resultRps.setPosition(neuPos);
			}
			break;

		}
		case "LEFT": {
			neuPos = oldPos.add(Vector2DInt.LEFT);
			if (testPosition(neuPos)) {
				resultRps.setPosition(neuPos);
			}
			break;

		}
		case "RIGHT": {
			neuPos = oldPos.add(Vector2DInt.RIGHT);
			if (testPosition(neuPos)) {
				resultRps.setPosition(neuPos);
			}
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}

		return resultRps;

	}

	@Override
	public double getCost(State s, String action, State succ) {
		return 1;
	}

	protected boolean testPosition(Vector2DInt pos) {
		if (pos.x < 0) {
			return false;
		}
		if (pos.y < 0) {
			return false;
		}
		if (pos.x >= GAMESIZE) {
			return false;
		}
		if (pos.y >= GAMESIZE) {
			return false;
		}

		if (this.labyrinth[pos.x][pos.y] == 'w') {
			return false;
		}
		return true;
	}
}
