package ai_algorithm.problems.slidingTilePuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import settings.Settings;
import tools.Vector2DInt;

public class SlidingTileProblem extends Problem {

	List<SlidingTileTile> tiles = null;

	/**
	 * Random with seed
	 */
	private Random rand = new Random(192);
	/**
	 * How often is mixed
	 */
	int SCHUFFLE_MAX = 30;

	/**
	 * Problemsize
	 * 
	 */
	Vector2DInt size;

	/**
	 * start configuration
	 */
	SlidingTileState startState;
	/**
	 * shuffled
	 */
	SlidingTileState goalState;

	public SlidingTileProblem() {
		this(4, 2);
	}

	/**
	 * Constructor with {@link Problem} dimensions
	 * 
	 * @param with
	 * @param height
	 */
	private SlidingTileProblem(int with, int height) {

		this.size = new Vector2DInt(with, height);

		SlidingTileTile[][] goalField = new SlidingTileTile[height][with];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < with; x++) {
				goalField[y][x] = new SlidingTileTile(x, y, y * with + x);

			}
		}

		goalState = new SlidingTileState(this, goalField);
		startState = shuffle(goalState);

		System.out.println(Arrays.deepToString(SlidingTileState.arrayDeepCoppy(goalState.getField())));
		System.out.println(Arrays.deepToString(SlidingTileState.arrayDeepCoppy(startState.getField())));

	}

	/**
	 * Selects a random action for a given state.
	 * 
	 * @param state
	 * @return
	 */
	private String randomAction(State state) {
		List<String> acc = getActions(state);
		// nur positive ints
		int r = (rand.nextInt() & Integer.MAX_VALUE) % acc.size();
		return acc.get(r);
	}

	/**
	 * shuffles the puzzle
	 * 
	 * @param state
	 * @return shuffle-state
	 */
	private SlidingTileState shuffle(SlidingTileState state) {
		SlidingTileState shuff = new SlidingTileState(this, state.getField());
		for (int i = 0; i < SCHUFFLE_MAX; i++) {
			shuff = (SlidingTileState) getSuccessor(shuff, randomAction(shuff));
		}
		return shuff;
	}

	/**
	 * returns the problem size
	 * 
	 * @return
	 */
	public Vector2DInt getSize() {
		return size;
	}

	@Override
	public State getInitialState() {
		return startState;
	}

	@Override
	public boolean isGoalState(State state) {
		if (state instanceof SlidingTileState stateT) {
			return goalState.equals(stateT);
		}

		return false;
	}

	@Override
	public List<String> getActions(State state) {
		List<String> list = new LinkedList<>();
		list.add("UP");
		list.add("DOWN");
		list.add("LEFT");
		list.add("RIGHT");
		return list;
	}

	@Override
	public State getSuccessor(State state, String action) {
		SlidingTileState stateT = (SlidingTileState) state;
		SlidingTileState succ = new SlidingTileState(this, null);
		// SlidingTileTile[][] field = stateT.getField();
		SlidingTileTile[][] field = stateT.getField();

		switch (action) {
		case "UP": {
			// find 0
			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					// 0 found
					if (field[i][j].getNum() == 0) {
						// switch 0 with tile
						try {//
							if (Settings.DEBUGMODE)
								System.out.println("UP");
							SlidingTileTile tmp = field[i][j];
							field[i][j] = field[i][j - 1];
							field[i][j - 1] = tmp;
							succ.setField(field);
							return succ;

						} catch (Exception e) {
							if (Settings.DEBUGMODE)
								System.out.println("EX UP");
							return stateT;
						}
					}
				}
			}
			succ.setField(field);
			break;
		}
		case "DOWN": {

			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					if (field[i][j].getNum() == 0) {
						try {
							if (Settings.DEBUGMODE)
								System.out.println("DOWN");
							SlidingTileTile tmp = field[i][j];
							field[i][j] = field[i][j + 1];
							field[i][j + 1] = tmp;
							succ.setField(field);
							return succ;

						} catch (Exception e) {
							if (Settings.DEBUGMODE)
								System.out.println("EX DOWN");
							return stateT;
						}
					}

				}
			}

			break;
		}
		case "LEFT": {

			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					if (field[i][j].getNum() == 0) {
						try {
							if (Settings.DEBUGMODE)
								System.out.println("LEFT");
							SlidingTileTile tmp = field[i][j];
							field[i][j] = field[i - 1][j];
							field[i - 1][j] = tmp;
							succ.setField(field);
							return succ;

						} catch (Exception e) {
							if (Settings.DEBUGMODE)
								System.out.println("EX LEFT");
							return stateT;
						}
					}

				}
			}
			succ.setField(field);
			break;
		}
		case "RIGHT": {

			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					if (field[i][j].getNum() == 0) {
						try {
							if (Settings.DEBUGMODE)
								System.out.println("RIGHT");
							SlidingTileTile tmp = field[i][j];
							field[i][j] = field[i + 1][j];
							field[i + 1][j] = tmp;
							succ.setField(field);
							return succ;

						} catch (Exception e) {
							if (Settings.DEBUGMODE)
								System.out.println("EX RIGHT");
							return stateT;
						}
					}

				}
			}
			succ.setField(field);
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}

		return succ;
	}

	@Override
	public State getGoalState() {
		return goalState;
	}

	@Override
	public double getCost(State s, String action, State succ) {
		return 1;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(goalState.getField());
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
