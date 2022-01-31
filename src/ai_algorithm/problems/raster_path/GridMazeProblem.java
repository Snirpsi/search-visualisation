package ai_algorithm.problems.raster_path;

import java.util.LinkedList;
import java.util.List;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import tools.Vector2DInt;

public class GridMazeProblem extends Problem {
	/**
	 * field size
	 */
	public final int GAMESIZE = 7;

	/**
	 * field
	 */
	public char[][] labyrinth;

	/**
	 * Starting position
	 */
	Vector2DInt startPos = new Vector2DInt(3, 2);// new Vector2DInt(0, 0);
	/**
	 * Goal position
	 */
	Vector2DInt goalPos = new Vector2DInt(GAMESIZE - 3, GAMESIZE - 2);// new Vector2DInt(GAMESIZE - 1, GAMESIZE - 1);

	/**
	 * Standard constructor that generates the maze
	 */
	public GridMazeProblem() {
		super();

		startPos = new Vector2DInt((int) (Math.random() * GAMESIZE), (int) (Math.random() * GAMESIZE));
		goalPos = new Vector2DInt((int) (Math.random() * GAMESIZE), (int) (Math.random() * GAMESIZE));
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

//		labyrinth = new char[][] { //
//				{ 'e', 'e', 'e', 'e', 'e', 'w', 'e' }, //
//				{ 'e', 'w', 'e', 'e', 'e', 'w', 'e' }, //
//				{ 'e', 'e', 'w', 'e', 'e', 'e', 'e' }, //
//				{ 'e', 'e', 'e', 'w', 'e', 'w', 'e' }, //
//				{ 'e', 'e', 'e', 'w', 'w', 'e', 'e' }, //
//				{ 'w', 'w', 'e', 'e', 'e', 'w', 'e' }, //
//				{ 'e', 'e', 'e', 'e', 'e', 'e', 'e' } //
//		};

	}

	/**
	 * Returns start {@link State} of the maze
	 * 
	 * @return start state
	 */
	@Override
	public State getInitialState() {
		return new GridMazeState(this, startPos);
	}

	/**
	 * Since the goal {@link State} is known from the beginning and there is only
	 * one it will be returned
	 */
	@Override
	public State getGoalState() {
		return new GridMazeState(this, goalPos);
	}

	/**
	 * Returns a {@link State} corresponding to the position
	 * 
	 * @param Quadrant whose state is to be obtained
	 * @return State with corresponding coordinates
	 */
	public State getCorresphrondingState(Vector2DInt coordinate) {
		if (!this.testPosition(coordinate)) {
			return null;

		}
		return new GridMazeState(this, coordinate);
	}

	/**
	 * Checks whether a given state is a target state
	 * 
	 * @param State to be tested
	 * @return true if state is goal{@link State} else false
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
	 * Specifies all possible actions for a state in the form of a list
	 * 
	 * @param state State to be tested 
	 * @return a list of possible actions
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
	 * Specifies the subsequent state for a state and an action
	 * 
	 * @param state   Initial state
	 * @param action Action to be applied
	 * 
	 * @return Sequent state
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
	 * Specifies the action cost to move from one state to the next

	 * 
	 * @param state  Initial state
	 * @param action Action to be applied
	 * @param succ   Successor state
	 * 
	 * @return Aktionskosten
	 */
	@Override
	public double getCost(State state, String action, State succ) {
		return 1;
	}

	/**
	 * Checks if a position is valid/wall or not
	 * 
	 * @param pos position
	 * @return True or Flase depending on whether the position is free or not
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