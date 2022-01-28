package ai_algorithm.problems.raster_path;

import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import tools.Vector2DInt;

public class GridMazeProblem extends Problem {
	/**
	 * Spielfeldgroesse
	 */
	public final int GAMESIZE = 7;
	/**
	 * 
	 */
	// public final int TILESIZE = 20;
	// public final int BORDERSIZE = 1;

	public char[][] labyrinth;

	Vector2DInt startPos = new Vector2DInt(3, 2);// new Vector2DInt(0, 0);
	Vector2DInt goalPos = new Vector2DInt(GAMESIZE - 3, GAMESIZE - 2);// new Vector2DInt(GAMESIZE - 1, GAMESIZE - 1);

	/**
	 * Standartkonstruktor der das Labyrinth generiert
	 */
	public GridMazeProblem() {
		super();

//		startPos = new Vector2DInt((int) (Math.random() * GAMESIZE), (int) (Math.random() * GAMESIZE));

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

		labyrinth = new char[][] { //
				{ 'e', 'e', 'e', 'e', 'e', 'w', 'e' }, //
				{ 'e', 'w', 'e', 'e', 'e', 'w', 'e' }, //
				{ 'e', 'e', 'w', 'e', 'e', 'e', 'e' }, //
				{ 'e', 'e', 'e', 'w', 'e', 'w', 'e' }, //
				{ 'e', 'e', 'e', 'w', 'w', 'e', 'e' }, //
				{ 'w', 'w', 'e', 'e', 'e', 'w', 'e' }, //
				{ 'e', 'e', 'e', 'e', 'e', 'e', 'e' } //
		};

	}

	/**
	 * Gibt startposition des Labyrinths aus
	 */
	@Override
	public State getInitialState() {
		return new GridMazeState(this, startPos);
	}

	/**
	 * Da der zielzustand von anfang an bekannt ist und es nur eien gibt wird dieser
	 * zurückgegeben
	 */
	@Override
	public State getGoalState() {
		return new GridMazeState(this, goalPos);
	}

	/**
	 * Gibt einen zustand entsprechend der koordinate zurück
	 * 
	 * @param coordinate deren zustand erhalten werden soll
	 * @return Zustand mit entsprechenden Koordinaten
	 */
	public State getCorresphrondingState(Vector2DInt coordinate) {
		if (!this.testPosition(coordinate)) {
			return null;

		}
		return new GridMazeState(this, coordinate);
	}

	/**
	 * Prueft ob es sich bei einem gegebenen zustand um einen zielzustand dandelt
	 * 
	 * @param state Zu prüfender zustand
	 */
	@Override
	public boolean isGoalState(State state) {
		GridMazeState stateC = (GridMazeState) state;
		if (stateC.getPosition().equals(goalPos)) {
			return true;
		}
		return false;
	}

	/**
	 * Gibt für einen zustand alle möglichen aktionen in form einer liste an
	 * 
	 * @param state Zu prüfender zustand
	 * @return eine liste von möglichen aktionen
	 */
	@Override
	public List<String> getActions(State state) {
		LinkedList<String> l = new LinkedList<String>();

		if (state instanceof GridMazeState rpState) {

			Vector2DInt neuPos = rpState.getPosition().add(Vector2DInt.UP);
			if (testPosition(neuPos)) {
				l.add("UP");
			}
			neuPos = rpState.getPosition().add(Vector2DInt.DOWN);
			if (testPosition(neuPos)) {
				l.add("DOWN");
			}
			neuPos = rpState.getPosition().add(Vector2DInt.LEFT);
			if (testPosition(neuPos)) {
				l.add("LEFT");
			}
			neuPos = rpState.getPosition().add(Vector2DInt.RIGHT);
			if (testPosition(neuPos)) {
				l.add("RIGHT");
			}
		}
		return l;
	}

	/**
	 * Gibt für einen zustand und eine Aktion den Folgezustand an
	 * 
	 * @param state  Ausgangszustand
	 * @param action anzuwendende Aktion
	 * 
	 * @return Folgezustand
	 */
	@Override
	public State getSuccessor(State state, String action) {
		GridMazeState stateC = (GridMazeState) state;
		Vector2DInt oldPos = stateC.getPosition();
		Vector2DInt neuPos = new Vector2DInt(oldPos);

		GridMazeState resultRps = new GridMazeState(this, neuPos);
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

	/**
	 * Gibt die aktionskosten an um von einem zustand in den nächsten zu gelangen
	 * 
	 * @param state  Ausgangszustand
	 * @param action anzuwendende Aktion
	 * @param succ   Folgezustand
	 * 
	 * @return Aktionskosten
	 */
	@Override
	public double getCost(State state, String action, State succ) {
		return 1;
	}

	/**
	 * Überprüft ob eine position gültig/wand ist oder nicht
	 * 
	 * @param pos
	 * @return True oder Flase jeh nachdem ob die position frei ist oder nicht
	 */
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
