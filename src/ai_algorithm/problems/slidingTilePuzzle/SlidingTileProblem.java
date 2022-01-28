package ai_algorithm.problems.slidingTilePuzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import ai_algorithm.search.DepthFirstSearch;
import settings.Settings;
import tools.Vector2DInt;

public class SlidingTileProblem extends Problem {

	
	private Random rand = new Random(192);

	int SCHUFFLE_MAX = 30;
	
	Vector2DInt size;

	Vector2DInt emptyTile;

	SlidingTileState startState;
	SlidingTileState goalState;

	public SlidingTileProblem() {
		this(4, 2);
	}

	private SlidingTileProblem(int with, int height) {

		this.size = new Vector2DInt(with, height);
		int[][] goalField = new int[height][with];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < with; j++) {
				goalField[i][j] = (i * (with)) + j;
			}
		}
		System.out.println(goalField[1][2]);

		goalState = new SlidingTileState(this, goalField);
		startState = shuffle(goalState);

		System.out.println(Arrays.deepToString(SlidingTileState.arrayDeepCoppy(goalState.getField())));
		System.out.println(Arrays.deepToString(SlidingTileState.arrayDeepCoppy(startState.getField())));
	}

	private String randomAction(State state) {
		List<String> acc = getActions(state);
		// nur positive ints
		int r = (rand.nextInt() & Integer.MAX_VALUE) % acc.size();
		return acc.get(r);
	}

	private SlidingTileState shuffle(SlidingTileState state) {
		SlidingTileState shuff = new SlidingTileState(this, state.getField());
		for (int i = 0; i < SCHUFFLE_MAX; i++) {
			shuff = (SlidingTileState) getSuccessor(shuff, randomAction(shuff));
		}
		return shuff;
	}

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
		int[][] field = stateT.getField();
		switch (action) {
		case "UP": {
			// suche null
			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					// 0 gefunden
					if (field[i][j] == 0) {
						// tausche 0 mit obendrüber
						try {
							if (Settings.DEBUGMODE)
								System.out.println("UP");
							field[i][j] = field[i][j - 1];
							field[i][j - 1] = 0;
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

			// suche null
			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					// 0 gefunden
					if (field[i][j] == 0) {
						// tausche 0 mit obendrüber
						try {
							if (Settings.DEBUGMODE)
								System.out.println("DOWN");
							field[i][j] = field[i][j + 1];
							field[i][j + 1] = 0;
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

			// suche null
			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					// 0 gefunden
					if (field[i][j] == 0) {
						// tausche 0 mit obendrüber
						try {
							if (Settings.DEBUGMODE)
								System.out.println("LEFT");
							field[i][j] = field[i - 1][j];
							field[i - 1][j] = 0;
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

			// suche null
			for (int i = 0; i < size.y; i++) {
				for (int j = 0; j < size.x; j++) {
					// 0 gefunden
					if (field[i][j] == 0) {
						// tausche 0 mit obendrüber
						try {
							if (Settings.DEBUGMODE)
								System.out.println("RIGHT");
							field[i][j] = field[i + 1][j];
							field[i + 1][j] = 0;
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
	public double getCost(State s, String action, State succ) {
		return 1;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(goalState.getField());
	}

}
